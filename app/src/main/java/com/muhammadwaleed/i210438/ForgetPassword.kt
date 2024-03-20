package com.muhammadwaleed.i210438

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ForgetPassword : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        val emailEditText: EditText = findViewById(R.id.etEmail)
        val sendButton: Button = findViewById(R.id.btnSend)
        val loginTextView: TextView = findViewById(R.id.tvLogin)

        sendButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show()
            } else {
                checkEmailInDatabase(email)
            }
        }

        loginTextView.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun checkEmailInDatabase(email: String) {
        database.child("Users").orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Email exists in the database, proceed to reset password
                        val intent = Intent(this@ForgetPassword, ResetPasswordActivity::class.java)
                        intent.putExtra("email", email)
                        startActivity(intent)
                        finish()
                    } else {
                        // Email does not exist in the database
                        Toast.makeText(this@ForgetPassword, "Email not found", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(this@ForgetPassword, "Database error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
