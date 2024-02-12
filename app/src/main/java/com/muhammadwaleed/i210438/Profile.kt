package com.muhammadwaleed.i210438

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val editProfileButton = findViewById<ImageButton>(R.id.imageButton3)

        editProfileButton.setOnClickListener {
            val intent = Intent(this, Editprofile::class.java)
            startActivity(intent)
        }



        val bookedSessionsButton: Button = findViewById(R.id.button7)
        bookedSessionsButton.setOnClickListener {
            val intent = Intent(this, Bookedsession::class.java)
            startActivity(intent)
        }

        val threeDotsButton = findViewById<ImageButton>(R.id.imageButton6)

        threeDotsButton.setOnClickListener {
            val intent = Intent(this, Notifications::class.java)
            startActivity(intent)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewFavouriteMentors)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val favoriteMentors = listOf(
            Mentor("John Cooper", "UX Designer", "Available", true),
            Mentor("Emma Phillips", "Lead Technology Officer", "Available", true),
            Mentor("Micheals", "UX developer", "Available", true),
            Mentor("Emma Phillips", "Lead Technology Officer", "Available", true),

            )

        val adapter = FavouriteMentorsAdapter(favoriteMentors)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)


        val reviewsRecyclerView = findViewById<RecyclerView>(R.id.recyclerViewReviews)
        reviewsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val reviewsList = listOf(
            Review("John Cooper", "John provided excellent prototyping techniques and insights", 5f),
            Review("Emma Phillips", "Her tips were valuable. Would love to connect again.", 4.5f),
            Review("Micheal Phillips", "Her tips were valuable. Would love to connect again.", 4.5f),

            )

        val reviewsAdapter = ReviewsAdapter(reviewsList)
        reviewsRecyclerView.adapter = reviewsAdapter
        reviewsRecyclerView.setHasFixedSize(true)


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
                    val intent = Intent(this, Search::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }



    }
}