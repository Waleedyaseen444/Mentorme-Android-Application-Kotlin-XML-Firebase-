package com.muhammadwaleed.i210438

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class Call : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

        val source = intent.getStringExtra("source")

        val imageButton24 = findViewById<ImageButton>(R.id.imageButton24)
        imageButton24.setOnClickListener {
            if (source == "Chatopen") {
                val intent = Intent(this, Chatopen::class.java)
                startActivity(intent)
            } else if (source == "Communitychat") {
                val intent = Intent(this, Communitychat::class.java)
                startActivity(intent)
            }
        }
    }
}
