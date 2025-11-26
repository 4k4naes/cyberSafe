package com.example.cybersafeapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun PasswordGeneratorScreen() {
    var password by remember { mutableStateOf("") }
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Generator Haseł", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            readOnly = true,
            label = { Text("Twoje hasło") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(onClick = { password = generatePassword(16) }) {
            Text("Generuj hasło")
        }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                if (password.isNotEmpty()) {
                    clipboardManager.setText(AnnotatedString(password))
                }
            },
            enabled = password.isNotEmpty()
        ) {
            Text("Kopiuj do schowka")
        }
    }
}

fun generatePassword(length: Int = 16): String {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#\$%^&*()-_=+[]{}<>?"
    return (1..length)
        .map { chars[Random.nextInt(chars.length)] }
        .joinToString("")
}
