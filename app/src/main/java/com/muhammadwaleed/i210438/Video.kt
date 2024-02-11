package com.muhammadwaleed.i210438

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class Video : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        val imageButton22 = findViewById<Button>(R.id.myButton)

        imageButton22.setOnClickListener {
            val intent = Intent(this, Add::class.java)
            startActivity(intent)
        }
    }
}