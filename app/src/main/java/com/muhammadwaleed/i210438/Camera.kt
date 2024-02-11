package com.muhammadwaleed.i210438

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class Camera : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        val addButton: Button = findViewById(R.id.myButton)

        addButton.setOnClickListener {
            val intent = Intent(this, Add::class.java)
            startActivity(intent)
        }
    }
}