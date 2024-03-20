package com.muhammadwaleed.i210438



import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class Notifications : AppCompatActivity() {
    private lateinit var notificationsAdapter: NotificationsAdapter
    private lateinit var notificationsRecyclerView: RecyclerView
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        notificationsRecyclerView = findViewById(R.id.notificationsRecyclerView)
        notificationsRecyclerView.layoutManager = LinearLayoutManager(this)

        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        notificationsRecyclerView.addItemDecoration(dividerItemDecoration)

        notificationsAdapter = NotificationsAdapter(mutableListOf()) { notification ->
            // Handle notification dismissal
            // You can choose to remove the notification from Firebase here
        }
        notificationsRecyclerView.adapter = notificationsAdapter

        val clearAllButton = findViewById<Button>(R.id.button8)
        clearAllButton.setOnClickListener {
            notificationsAdapter.clearAll()
            // You can also choose to clear notifications from Firebase here
        }

        val backButton = findViewById<ImageButton>(R.id.imageButton7)
        backButton.setOnClickListener {
            finish()
        }

        // Initialize Firebase database reference
        database = FirebaseDatabase.getInstance().reference.child("notifications")
        fetchNotificationsFromFirebase()
    }

    private fun fetchNotificationsFromFirebase() {
        // Listen for changes in the "notifications" node
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val notificationsList = mutableListOf<Notification>()
                for (childSnapshot in snapshot.children) {
                    val id = childSnapshot.key // Get the key as the notification ID
                    val message = childSnapshot.child("message").getValue(String::class.java)
                    val timestamp = childSnapshot.child("timestamp").getValue(Long::class.java)

                    if (id != null && message != null && timestamp != null) {
                        val notification = Notification(id, message, timestamp)
                        notificationsList.add(notification)
                    }
                }

                notificationsAdapter.setData(notificationsList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Notifications", "Failed to read notifications.", error.toException())
            }
        })
    }

}
