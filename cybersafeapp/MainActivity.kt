package com.example.cybersafeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.cybersafeapp.ui.screens.CyberNewsScreen
import com.example.cybersafeapp.ui.screens.EmailLeakScreen
import com.example.cybersafeapp.ui.screens.GuideScreen
import com.example.cybersafeapp.ui.screens.IpCheckerScreen
import com.example.cybersafeapp.ui.screens.PasswordGeneratorScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CyberSafeApp()
            }
        }
    }
}

@Composable
fun CyberSafeApp() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("IP Checker", "CyberNews", "Poradnik", "Email Leak Check", "Generator Haseł")

    Scaffold(
        bottomBar = {
            NavigationBar {
                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        label = null,
                        icon = {
                            Image(
                                painter = painterResource(
                                    id = when (index) {
                                        0 -> R.drawable.ic_ip
                                        1 -> R.drawable.ic_news
                                        2 -> R.drawable.ic_guide
                                        3 -> R.drawable.ic_email
                                        else -> R.drawable.ic_password
                                    }
                                ),
                                contentDescription = title
                            )
                        }
                    )
                }
            }
        }
    ) { padding ->
        Box(Modifier.padding(padding).fillMaxSize()) {
            when (selectedTab) {
                0 -> IpCheckerScreen()
                1 -> CyberNewsScreen()
                2 -> GuideScreen()
                3 -> EmailLeakScreen()
                4 -> PasswordGeneratorScreen()
            }
        }
    }
}

@Composable
fun PlaceholderScreen(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        androidx.compose.material3.Text(text)
    }
}
