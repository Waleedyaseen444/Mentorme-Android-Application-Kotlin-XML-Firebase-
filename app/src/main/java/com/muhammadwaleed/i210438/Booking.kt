package com.muhammadwaleed.i210438

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class Booking : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        val backButton = findViewById<ImageButton>(R.id.imageButton14)
        val bookAppointmentButton = findViewById<Button>(R.id.button5)

        backButton.setOnClickListener {
            val intent = Intent(this, Mentordetails::class.java)
            startActivity(intent)
        }

        bookAppointmentButton.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }
    }
}