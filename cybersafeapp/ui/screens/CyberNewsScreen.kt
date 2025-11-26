package com.example.cybersafeapp.ui.screens

import android.content.Intent
import android.net.Uri
import android.util.Xml
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import java.net.URL

data class NewsItem(val title: String, val link: String)

@Composable
fun CyberNewsScreen() {
    var news by remember { mutableStateOf<List<NewsItem>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        news = fetchCyberNews()
        loading = false
    }

    if (loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        NewsList(news)
    }
}

@Composable
fun NewsList(news: List<NewsItem>) {
    val context = LocalContext.current
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(news) { item ->
            Card(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.link))
                        context.startActivity(intent)
                    }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(item.title, style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}

suspend fun fetchCyberNews(): List<NewsItem> = withContext(Dispatchers.IO) {
    val newsList = mutableListOf<NewsItem>()
    try {
        val url = URL("https://www.securityweek.com/feed/")
        val parser = Xml.newPullParser()
        parser.setInput(url.openStream(), null)

        var eventType = parser.eventType
        var currentTitle: String? = null
        var currentLink: String? = null
        var insideItem = false

        while (eventType != XmlPullParser.END_DOCUMENT) {
            val name = parser.name
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    if (name.equals("item", ignoreCase = true)) {
                        insideItem = true
                    } else if (insideItem) {
                        if (name.equals("title", ignoreCase = true)) {
                            currentTitle = parser.nextText()
                        } else if (name.equals("link", ignoreCase = true)) {
                            currentLink = parser.nextText()
                        }
                    }
                }
                XmlPullParser.END_TAG -> {
                    if (name.equals("item", ignoreCase = true)) {
                        if (!currentTitle.isNullOrBlank() && !currentLink.isNullOrBlank()) {
                            newsList.add(NewsItem(currentTitle, currentLink))
                        }
                        currentTitle = null
                        currentLink = null
                        insideItem = false
                    }
                }
            }
            eventType = parser.next()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    newsList
}
