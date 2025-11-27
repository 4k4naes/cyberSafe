package com.example.cybersafeapp.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cybersafeapp.R
import kotlin.random.Random

class PasswordGeneratorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_password_generator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val passwordField = view.findViewById<EditText>(R.id.password_output)
        val generateButton = view.findViewById<Button>(R.id.button_generate)
        val copyButton = view.findViewById<Button>(R.id.button_copy)

        generateButton.setOnClickListener {
            val newPassword = generatePassword(16)
            passwordField.setText(newPassword)
        }

        copyButton.setOnClickListener {
            val text = passwordField.text.toString()
            if (text.isEmpty()) {
                Toast.makeText(requireContext(), "Brak hasła do skopiowania", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(ClipData.newPlainText("password", text))

            Toast.makeText(requireContext(), "Skopiowano do schowka", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generatePassword(length: Int = 16): String {
        val chars =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#\$%^&*()-_=+[]{}<>?"
        return (1..length)
            .map { chars[Random.nextInt(chars.length)] }
            .joinToString("")
    }
}
