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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.security.MessageDigest

// TODO: nie dziala api cale do poprawy
class EmailLeakFragment : Fragment() {

    private val client = OkHttpClient()
    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_email_leak, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val passwordInput = view.findViewById<EditText>(R.id.password_input)
        val checkButton = view.findViewById<Button>(R.id.check_button)
        val resultText = view.findViewById<TextView>(R.id.result_text)

        checkButton.setOnClickListener {
            val password = passwordInput.text.toString().trim()

            if (password.isEmpty()) {
                resultText.text = "Wpisz hasło!"
                return@setOnClickListener
            }

            scope.launch {
                resultText.text = "Sprawdzanie w HIBP..."
                val leakCount = checkPasswordHIBP(password)

                resultText.text = when {
                    leakCount > 0 -> "Hasło wyciekło $leakCount razy!"
                    leakCount == 0 -> "Nie znaleziono hasła w żadnych wyciekach."
                    else -> "Błąd połączenia z API."
                }
            }
        }
    }

    private suspend fun checkPasswordHIBP(password: String): Int =
        withContext(Dispatchers.IO) {
            try {
                val sha1 = sha1(password).uppercase()
                val prefix = sha1.substring(0, 5)
                val suffix = sha1.substring(5)

                val request = Request.Builder()
                    .url("https://api.pwnedpasswords.com/range/$prefix")
                    .header("User-Agent", "CybersafeApp/1.0")
                    .build()

                val response = client.newCall(request).execute()
                if (!response.isSuccessful) return@withContext -1

                val body = response.body?.string() ?: return@withContext -1
                val lines = body.split("\n")

                for (line in lines) {
                    val parts = line.split(":")
                    if (parts.size == 2) {
                        val hashSuffix = parts[0]
                        val count = parts[1].toIntOrNull() ?: 0
                        if (hashSuffix.equals(suffix, ignoreCase = true)) {
                            return@withContext count
                        }
                    }
                }

                return@withContext 0
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext -1
            }
        }

    private fun sha1(input: String): String {
        val digest = MessageDigest.getInstance("SHA-1")
        val bytes = digest.digest(input.toByteArray())
        return bytes.joinToString(separator = "") { byte -> String.format("%02x", byte) }
    }
}