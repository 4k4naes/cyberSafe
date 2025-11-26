package com.example.cybersafeapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.security.MessageDigest

@Composable
fun EmailLeakScreen() {
    var password by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Wpisz hasło") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                if (password.isNotEmpty()) {
                    scope.launch {
                        val count = checkPassword(password)
                        result = if (count > 0) {
                            "Twoje hasło wyciekło $count razy!"
                        } else {
                            "Twoje hasło nie znajduje się w znanych wyciekach."
                        }
                    }
                } else {
                    result = "Wpisz hasło, aby sprawdzić."
                }
            }) {
                Text("Sprawdź hasło")
            }

            Spacer(modifier = Modifier.height(16.dp))

            result?.let { Text(it) }
        }
    }
}

fun sha1(input: String): String {
    val bytes = MessageDigest.getInstance("SHA-1").digest(input.toByteArray(Charsets.UTF_8))
    return bytes.joinToString("") { "%02X".format(it) }
}

suspend fun checkPassword(password: String): Int = withContext(Dispatchers.IO) {
    val client = OkHttpClient()
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
                    val hashPart = parts[0].trim()
                    val countPart = parts[1].trim()
                    if (hashPart.equals(suffix, ignoreCase = true)) {
                        return@withContext countPart.toIntOrNull() ?: 0
                    }
                }
            }
        }
    } catch (e: Exception) {
        Log.e("EmailLeakScreen", "Błąd przy sprawdzaniu hasła", e)
    }
    return@withContext 0
}

