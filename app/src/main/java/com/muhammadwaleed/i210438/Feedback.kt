package com.muhammadwaleed.i210438

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import android.widget.RatingBar

class Feedback : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        val backButton = findViewById<ImageButton>(R.id.imageButton14)
        val submitFeedbackButton = findViewById<Button>(R.id.button)

        backButton.setOnClickListener {
            val intent = Intent(this, Mentordetails::class.java)
            startActivity(intent)
        }

        submitFeedbackButton.setOnClickListener {
            val intent = Intent(this, Mentordetails::class.java)
            startActivity(intent)
        }

    }
}