package com.muhammadwaleed.i210438

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import android.content.Context
import android.content.SharedPreferences


class Chat : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MessagesAdapter
    private lateinit var communityAdapter: CommunityAdapter
    private lateinit var communityMembers: MutableList<CommunityMember>
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        communityMembers = mutableListOf()
        communityAdapter = CommunityAdapter(communityMembers) { communityMember ->
            // When a community member is clicked, pass data to Communitychat
            val intent = Intent(this, Communitychat::class.java)
            intent.putExtra("COMMUNITY_MEMBER_IMAGE_URL", communityMember.imageUrl)
            intent.putExtra("MENTOR_NAME", communityMember.mentorName)
            startActivity(intent)
        }

        databaseReference = FirebaseDatabase.getInstance().reference.child("community_members")
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    val imageUrl = ds.child("imageUrl").getValue(String::class.java)
                    val mentorName = ds.child("mentorName").getValue(String::class.java)
                    imageUrl?.let {
                        communityMembers.add(CommunityMember(it, mentorName ?: ""))
                    }
                }
                communityAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })

        val communityRecyclerView = findViewById<RecyclerView>(R.id.community_recycler_view)
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
        adapter = MessagesAdapter(getPersonList()) {
            val intent = Intent(this, Chatopen::class.java)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        // Check if an image URL and mentor's name were passed from Mentordetails
        val communityMemberImageUrl = intent.getStringExtra("COMMUNITY_MEMBER_IMAGE_URL")
        val mentorName = intent.getStringExtra("MENTOR_NAME")
        if (!communityMemberImageUrl.isNullOrEmpty() && !mentorName.isNullOrEmpty()) {
            // Check if the community member already exists
            var memberExists = false
            for (member in communityMembers) {
                if (member.imageUrl == communityMemberImageUrl && member.mentorName == mentorName) {
                    memberExists = true
                    break
                }
            }

            // If the member doesn't exist, add to Firebase and local list
            if (!memberExists) {
                val newMemberId = databaseReference.push().key
                newMemberId?.let { id ->
                    val memberData = mapOf(
                        "imageUrl" to communityMemberImageUrl,
                        "mentorName" to mentorName
                    )
                    databaseReference.child(id).setValue(memberData)
                    communityMembers.add(CommunityMember(communityMemberImageUrl, mentorName))
                    communityAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun getPersonList(): List<Person> {
        return listOf(
            Person("John Cooper", "1 New Message", R.drawable.imag1),
            Person("Jack Watson", "No New Messages", R.drawable.imag2),
            Person("Emma Phillips", "No New Messages", R.drawable.imag3)
        )
    }
}
