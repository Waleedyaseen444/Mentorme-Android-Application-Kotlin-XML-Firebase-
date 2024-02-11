package com.muhammadwaleed.i210438

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class Mentordetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mentordetails)

        val backButton = findViewById<ImageButton>(R.id.imageButton14)
        val dropReviewButton = findViewById<Button>(R.id.button11)
        val bookSessionButton = findViewById<Button>(R.id.button10)

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }

        dropReviewButton.setOnClickListener {
            val intent = Intent(this, Feedback::class.java)
            startActivity(intent)
        }

        bookSessionButton.setOnClickListener {
            val intent = Intent(this, Booking::class.java)
            startActivity(intent)
        }

    }
}