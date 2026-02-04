package com.example.cybersafeapp.ui.screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cybersafeapp.R
import org.json.JSONObject
import java.net.URL
import kotlin.concurrent.thread

class CyberNewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_cyber_news, container, false)

        val recycler = view.findViewById<RecyclerView>(R.id.newsRecycler)
        recycler.layoutManager = LinearLayoutManager(requireContext())

        thread {
            val response = URL(
                "https://hn.algolia.com/api/v1/search_by_date?query=security"
            ).readText()

            val json = JSONObject(response)
            val hits = json.getJSONArray("hits")

            val newsList = mutableListOf<NewsItem>()

            for (i in 0 until hits.length()) {
                val item = hits.getJSONObject(i)

                val title = item.optString("title", "Brak tytułu")
                val description = item.optString("story_text", "Brak opisu")
                val url = item.optString("url", "")
                val date = item.optString("created_at", "").take(10)

                if (url.isNotEmpty()) {
                    newsList.add(
                        NewsItem(
                            title = title,
                            description = description,
                            url = url,
                            date = date
                        )
                    )
                }
            }

            requireActivity().runOnUiThread {
                recycler.adapter = NewsAdapter(newsList)
            }
        }

        return view
    }

    data class NewsItem(
        val title: String,
        val description: String,
        val url: String,
        val date: String
    )

    inner class NewsAdapter(
        private val items: List<NewsItem>
    ) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val title: TextView = view.findViewById(R.id.title)
            val description: TextView = view.findViewById(R.id.description)
            val date: TextView = view.findViewById(R.id.date)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(R.layout.item_news, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]

            holder.title.text = item.title
            holder.description.text = item.description
            holder.date.text = item.date

            holder.itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int = items.size
    }
}
