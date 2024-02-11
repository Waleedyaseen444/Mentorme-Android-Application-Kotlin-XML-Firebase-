package com.muhammadwaleed.i210438

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val loginTextView: TextView = findViewById(R.id.login)
        loginTextView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        val signupBtn = findViewById<Button>(R.id.signup)
        signupBtn.setOnClickListener {
            val phNo=findViewById<EditText>(R.id.editTextPhone)
            val intent = Intent(this, VerifyActivity:: class.java).apply{
                val phoneNumber = phNo.text.toString()
                putExtra("Phone",phoneNumber)

        }
            startActivity(intent)


        }
    }
}
