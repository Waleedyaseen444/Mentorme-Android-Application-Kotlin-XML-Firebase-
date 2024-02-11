package com.muhammadwaleed.i210438

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class Communitychat : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_communitychat)

        val imageButton15 = findViewById<ImageButton>(R.id.imageButton15)
        val imageButton16 = findViewById<ImageButton>(R.id.imageButton16)
        val imageButton17 = findViewById<ImageButton>(R.id.imageButton17)

        imageButton15.setOnClickListener {
            val intent = Intent(this, Chat::class.java)
            startActivity(intent)
        }

        imageButton16.setOnClickListener {
            val intent = Intent(this, Call::class.java)
            intent.putExtra("source", "Communitychat")
            startActivity(intent)
        }


        imageButton17.setOnClickListener {
            val intent = Intent(this, Videocall::class.java)
            intent.putExtra("source", "Communitychat")
            startActivity(intent)
        }
    }
}