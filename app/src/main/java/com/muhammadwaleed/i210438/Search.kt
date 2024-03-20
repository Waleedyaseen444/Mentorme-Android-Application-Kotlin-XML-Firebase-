package com.muhammadwaleed.i210438

import RecentSearchesAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class Search : AppCompatActivity() {
    private lateinit var recentSearchesAdapter: RecentSearchesAdapter
    private val recentSearchesList = mutableListOf("Mentor 1", "Mentor 2", "Mentor 3")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setupRecentSearchesRecyclerView()

        val searchBar = findViewById<EditText>(R.id.search_bar)
        val searchButton = findViewById<ImageButton>(R.id.btnSearch)
        searchButton.setOnClickListener {
            val query = searchBar.text.toString().trim()
            if (query.isNotEmpty()) {
                performSearch(query)
            } else {
                Toast.makeText(this, "Please enter a search query", Toast.LENGTH_SHORT).show()
            }
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val intent = Intent(this, MainActivity3::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_add -> {
                    val intent = Intent(this, Add::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_chat -> {
                    val intent = Intent(this, Chat::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_profile -> {
                    val intent = Intent(this, Profile::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_search -> {
                    true
                }
                else -> false
            }
        }
    }

    private fun setupRecentSearchesRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_recent_searches)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recentSearchesAdapter = RecentSearchesAdapter(recentSearchesList,
            onItemClick = { query ->
                performSearch(query)
            },
            onItemRemoved = { removedItem ->
                recentSearchesList.remove(removedItem)
                recentSearchesAdapter.notifyDataSetChanged()
                // Handle item removed if needed
            })
        recyclerView.adapter = recentSearchesAdapter
    }

    private fun performSearch(query: String) {
        val intent = Intent(this, Searchresults::class.java)
        intent.putExtra("query", query)
        startActivity(intent)
    }
}
