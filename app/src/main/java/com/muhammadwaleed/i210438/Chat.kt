package com.muhammadwaleed.i210438

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.recyclerview.widget.RecyclerView

class Chat : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MessagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


        val communityRecyclerView = findViewById<RecyclerView>(R.id.community_recycler_view)

        val communityAdapter = CommunityAdapter(getCommunityMembers()) { communityMember ->
            val intent = Intent(this, Communitychat::class.java)
            startActivity(intent)
        }

        adapter = MessagesAdapter(getPersonList()) { person ->

            val intent = Intent(this, Chatopen::class.java)
            intent.putExtra("image", person.imageResource)
            startActivity(intent)

        }
        communityRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        communityRecyclerView.adapter = communityAdapter
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

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


    }

    private fun getPersonList(): List<Person> {
        return listOf(
            Person("John Cooper", "1 New Message", R.drawable.imag1),
            Person("Jack Watson", "No New Messages", R.drawable.imag2),
            Person("Emma Phillips", "No New Messages", R.drawable.imag3)
        )
    }

    private fun getCommunityMembers(): List<CommunityMember> {
        return listOf(
            CommunityMember(R.drawable.imag1),
            CommunityMember(R.drawable.imag2),
            CommunityMember(R.drawable.imag3),
            CommunityMember(R.drawable.img4),
            CommunityMember(R.drawable.img5),

        )
    }

}