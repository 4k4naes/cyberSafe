package com.example.cybersafeapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun IpCheckerScreen() {
    var ip by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Sprawdź adres IP", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = ip,
            onValueChange = { ip = it },
            label = { Text("Adres IP") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /*api*/ }) {
            Text("Sprawdź IP")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { /*pobierz sprawdz ip*/ }) {
            Text("Sprawdź moje IP")
        }
    }
}
