package com.muhammadwaleed.i210438



import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class Notifications : AppCompatActivity() {
    private lateinit var notificationsAdapter: NotificationsAdapter
    private lateinit var notificationsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)



        notificationsRecyclerView = findViewById(R.id.notificationsRecyclerView)
        notificationsRecyclerView.layoutManager = LinearLayoutManager(this)

        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        notificationsRecyclerView.addItemDecoration(dividerItemDecoration)


        val notificationsList = mutableListOf(
            Notification(1, "You have added a mentor, Alex. Thank you!"),
            Notification(2, "Your favorites list is expanding. Impressive"),
            Notification(3, "You have a new message in community."),
            Notification(4, "Your favorite mentor is Online. Say Hi to him."),
            Notification(5, "Thank you for booking a session. Good Luck.")
        )

        notificationsAdapter = NotificationsAdapter(notificationsList) { notification ->
            notificationsAdapter.removeItem(notification)
        }

        notificationsRecyclerView.adapter = notificationsAdapter

        val clearAllButton = findViewById<Button>(R.id.button8)
        clearAllButton.setOnClickListener {
            notificationsAdapter.clearAll()
        }

        val backButton = findViewById<ImageButton>(R.id.imageButton7)
        backButton.setOnClickListener {
            finish()
        }
    }
}
