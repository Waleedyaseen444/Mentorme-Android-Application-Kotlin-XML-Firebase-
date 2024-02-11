package com.muhammadwaleed.i210438

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Bookedsession : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookedsession)

        val backButton = findViewById<ImageButton>(R.id.imageButton5)
        backButton.setOnClickListener {
            finish()
        }

        val sessionList = listOf(
            SessionDetails("John Cooper", "UX Designer @ Google", "24th Dec 2023", "1:00 pm", R.drawable.imag1),
            SessionDetails("Emma Phillips", "Android Developer", "1st Jan 2024", "9:00 pm", R.drawable.imag2)
        )

        val sessionsRecyclerView = findViewById<RecyclerView>(R.id.sessionsRecyclerView).apply {
            layoutManager = LinearLayoutManager(this@Bookedsession)
            adapter = SessionsAdapter(sessionList) { session ->

            }
            setHasFixedSize(true)
        }
    }
}