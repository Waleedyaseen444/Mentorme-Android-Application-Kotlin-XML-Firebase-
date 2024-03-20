package com.muhammadwaleed.i210438

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Bookedsession : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference
    private lateinit var sessionsRecyclerView: RecyclerView
    private lateinit var backButton: ImageButton
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookedsession)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        sessionsRecyclerView = findViewById(R.id.sessionsRecyclerView)
        backButton = findViewById(R.id.imageButton5)

        // Set up RecyclerView
        val layoutManager = LinearLayoutManager(this)
        sessionsRecyclerView.layoutManager = layoutManager

        // Retrieve sessions for the current user from Firebase and set up RecyclerView adapter
        val sessionList = mutableListOf<SessionDetails>()
        val adapter = SessionsAdapter(sessionList) { session ->
            // Handle click event here
            // For example, start a new activity with session details
        }

        sessionsRecyclerView.adapter = adapter

        // Get the current user's ID
        val userId = auth.currentUser?.uid

        if (userId != null) {
            myRef = database.getReference("users/$userId/booked_sessions")

            // Read data from Firebase
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    sessionList.clear()
                    for (postSnapshot in snapshot.children) {
                        val session = postSnapshot.getValue(SessionDetails::class.java)
                        session?.let { sessionList.add(it) }
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle database error
                }
            })
        }

        backButton.setOnClickListener {
            // This will finish the current activity and take you back to the previous one
            finish()
        }
    }
}
