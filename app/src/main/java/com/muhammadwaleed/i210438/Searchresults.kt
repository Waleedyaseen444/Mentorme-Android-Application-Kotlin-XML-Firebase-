package com.muhammadwaleed.i210438

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import com.muhammadwaleed.i210438.RecentAdapter.OnItemClickListener

class Searchresults : AppCompatActivity(), OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecentAdapter
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchresults)

        database = FirebaseDatabase.getInstance().reference.child("mentors")

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Extracting search query
        val query = intent.getStringExtra("query") ?: ""

        fetchMentors(query)

        val backButton = findViewById<ImageButton>(R.id.backBtn)
        backButton.setOnClickListener {
            val intent = Intent(this, Search::class.java)
            startActivity(intent)
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

    private fun fetchMentors(query: String) {
        val mentorList = mutableListOf<Recentdata>()

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mentorList.clear()
                for (dataSnapshot in snapshot.children) {
                    val mentor = dataSnapshot.getValue(Mentorhome::class.java)
                    mentor?.let {
                        // Check if mentor name or occupation contains the query
                        if (mentor.name?.contains(query, ignoreCase = true) == true ||
                            mentor.occupation?.contains(query, ignoreCase = true) == true) {
                            mentorList.add(Recentdata(
                                mentor.name ?: "",
                                mentor.occupation ?: "",
                                mentor.status ?: "",
                                mentor.pricePerSession ?: "",
                                mentor.imageUrl ?: "",
                            ))
                        }
                    }
                }
                Log.d("SearchResults", "Filtered List Size: ${mentorList.size}")

                // Set the adapter with click listener
                adapter = RecentAdapter(mentorList, this@Searchresults)
                recyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("SearchResults", "Firebase fetch error: ${error.message}")
            }
        })
    }

    // Handle item clicks
    override fun onItemClick(item: Recentdata) {
        val intent = Intent(this, Mentordetails::class.java).apply {
            putExtra("MENTOR_NAME", item.sampleText)
            putExtra("MENTOR_DESCRIPTION", item.occupationText)
            putExtra("MENTOR_IMAGE_URL", item.imageUrl)
        }
        startActivity(intent)
    }
}
