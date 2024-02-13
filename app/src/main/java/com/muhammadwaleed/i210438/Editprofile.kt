package com.muhammadwaleed.i210438

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton

class Editprofile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editprofile)

        val updateBtn = findViewById<Button>(R.id.IdUpdate)
        updateBtn.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        val back = findViewById<ImageButton>(R.id.backbutton)
        back.setOnClickListener {
            onBackPressed()
        }
    }
}
