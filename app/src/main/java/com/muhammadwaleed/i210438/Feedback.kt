package com.muhammadwaleed.i210438

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Feedback : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var mentorName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val backButton = findViewById<ImageButton>(R.id.imageButton14)
        val submitFeedbackButton = findViewById<Button>(R.id.button)
        val editText = findViewById<EditText>(R.id.editTextTextMultiLine)
        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)

        // Get mentor's name from intent
        mentorName = intent.getStringExtra("MENTOR_NAME") ?: ""
        val mentorNameTextView = findViewById<TextView>(R.id.textView44)
        mentorNameTextView.text = mentorName  // Display mentor's name as reviewer's name

        backButton.setOnClickListener {
            onBackPressed()
        }

        submitFeedbackButton.setOnClickListener {
            val reviewText = editText.text.toString().trim()
            val rating = ratingBar.rating

            if (reviewText.isNotEmpty()) {
                saveFeedbackToFirebase(mentorName, reviewText, rating)
            } else {
                Toast.makeText(this, "Please enter a review", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveFeedbackToFirebase(mentorName: String, reviewText: String, rating: Float) {
        val user = auth.currentUser
        val uid = user?.uid

        if (uid != null) {
            val ref = database.getReference("Feedbacks").child(uid).push()
            val feedback = FeedbackData(mentorName, reviewText, rating)
            ref.setValue(feedback)
                .addOnSuccessListener {
                    Toast.makeText(this, "Feedback submitted successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to submit feedback", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
