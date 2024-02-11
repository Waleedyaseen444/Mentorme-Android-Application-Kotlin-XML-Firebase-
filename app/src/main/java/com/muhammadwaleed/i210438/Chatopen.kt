package com.muhammadwaleed.i210438

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView

class Chatopen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatopen)

        val imageResId = intent.getIntExtra("image", 0)
        if (imageResId != 0) {
            val imageView = findViewById<ImageView>(R.id.person_image)
            imageView.setImageResource(imageResId)
        }
        val imageButton15 = findViewById<ImageButton>(R.id.imageButton15)
        val imageButton16 = findViewById<ImageButton>(R.id.imageButton16)
        val imageButton17 = findViewById<ImageButton>(R.id.imageButton17)

        imageButton15.setOnClickListener {
            val intent = Intent(this, Chat::class.java)
            startActivity(intent)
        }

        imageButton16.setOnClickListener {
            val intent = Intent(this, Call::class.java)
            intent.putExtra("source", "Chatopen")
            startActivity(intent)
        }


        imageButton17.setOnClickListener {
            val intent = Intent(this, Videocall::class.java)
            intent.putExtra("source", "Chatopen")
            startActivity(intent)
        }
    }
}