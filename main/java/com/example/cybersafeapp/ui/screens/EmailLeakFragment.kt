package com.example.cybersafeapp.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.cybersafeapp.R

class EmailLeakFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_email_leak, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val passwordInput = view.findViewById<EditText>(R.id.password_input)
        val checkButton = view.findViewById<Button>(R.id.check_button)
        val resultText = view.findViewById<TextView>(R.id.result_text)

        checkButton.setOnClickListener {
            val password = passwordInput.text.toString()

            if (password.isEmpty()) {
                resultText.text = "Wpisz hasło"
                return@setOnClickListener
            }

            val result = checkPasswordStrength(password)
            resultText.text = result
        }
    }


    private fun checkPasswordStrength(password: String): String {
        var score = 0
        val issues = mutableListOf<String>()

        if (password.length >= 12) score++ else issues.add("min. 12 znaków")
        if (password.any { it.isUpperCase() }) score++ else issues.add("wielka litera")
        if (password.any { it.isLowerCase() }) score++ else issues.add("mała litera")
        if (password.any { it.isDigit() }) score++ else issues.add("cyfra")
        if (password.any { !it.isLetterOrDigit() }) score++ else issues.add("znak specjalny")

        val commonPasswords = listOf(
            "123456", "password", "qwerty", "dupa1234", "admin", "letmein", "12345678"
        ) // probowalam z rockyou zeby sprawdzalo ale plik zaduzy by byl a wszedzie jest w zipach

        if (commonPasswords.any { password.contains(it, ignoreCase = true) }) {
            return "Bardzo słabe hasło\nZawiera popularne schematy"
        }

        return when (score) {
            5 -> "Mocne hasło\n"
            3, 4 -> "Średnie hasło\nBrakuje: ${issues.joinToString(", ")}"
            else -> "Słabe hasło\nBrakuje: ${issues.joinToString(", ")}"
        }
    }
}
