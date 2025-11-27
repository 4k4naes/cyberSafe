package com.example.cybersafeapp.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.cybersafeapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.security.MessageDigest

class EmailLeakFragment : Fragment() {

    private val client = OkHttpClient()
    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Łączymy fragment z XML
        return inflater.inflate(R.layout.fragment_email_leak, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val passwordInput = view.findViewById<EditText>(R.id.password_input)
        val checkButton = view.findViewById<Button>(R.id.check_button)
        val resultText = view.findViewById<TextView>(R.id.result_text)

        checkButton.setOnClickListener {
            val password = passwordInput.text.toString()

            if (password.isEmpty()) {
                resultText.text = "Wpisz hasło!"
                return@setOnClickListener
            }

            scope.launch {
                val count = checkPassword(password)
                resultText.text = if (count > 0) {
                    "Twoje hasło wyciekło $count razy!"
                } else {
                    "Twoje hasło nie znajduje się w znanych wyciekach."
                }
            }
        }
    }

    private fun sha1(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-1")
            .digest(input.toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { "%02X".format(it) }
    }

    private suspend fun checkPassword(password: String): Int = withContext(Dispatchers.IO) {
        val hash = sha1(password)
        val prefix = hash.substring(0, 5)
        val suffix = hash.substring(5)

        val request = Request.Builder()
            .url("https://api.pwnedpasswords.com/range/$prefix")
            .build()

        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return@withContext 0
                val body = response.body?.string() ?: return@withContext 0

                body.lines().forEach { line ->
                    val parts = line.split(":")
                    if (parts.size == 2) {
                        val hashPart = parts[0]
                        val countPart = parts[1]
                        if (hashPart.equals(suffix, ignoreCase = true)) {
                            return@withContext countPart.toIntOrNull() ?: 0
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("EmailLeakFragment", "Błąd API", e)
        }

        return@withContext 0
    }
}
