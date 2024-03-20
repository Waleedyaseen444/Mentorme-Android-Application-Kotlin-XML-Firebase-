package com.muhammadwaleed.i210438

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class Mentordetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mentordetails)

        val backButton = findViewById<ImageButton>(R.id.imageButton14)
        val dropReviewButton = findViewById<Button>(R.id.button11)
        val bookSessionButton = findViewById<Button>(R.id.button10)
        val joinCommunityButton = findViewById<Button>(R.id.button9) // Added button9
        val mentorImageView = findViewById<CircleImageView>(R.id.mentorImageView)

        // Receive mentor data from intent
        val mentorName = intent.getStringExtra("MENTOR_NAME")
        val mentorProfession = intent.getStringExtra("MENTOR_DESCRIPTION")
        val mentorImageUrl = intent.getStringExtra("MENTOR_IMAGE_URL")

        val nameTextView = findViewById<TextView>(R.id.textView32)
        val professionTextView = findViewById<TextView>(R.id.textView34)

        // Set received mentor data to TextViews
        nameTextView.text = mentorName
        professionTextView.text = mentorProfession

        // Load mentor image using Picasso
        if (!mentorImageUrl.isNullOrEmpty()) {
            Picasso.get().load(mentorImageUrl).into(mentorImageView)
        }

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }

        dropReviewButton.setOnClickListener {
            val intent = Intent(this, Feedback::class.java)
            // Pass mentor's name to Feedback activity
            intent.putExtra("MENTOR_NAME", mentorName)
            startActivity(intent)
        }

        bookSessionButton.setOnClickListener {
            val intent = Intent(this, Booking::class.java)
            intent.putExtra("MENTOR_NAME", mentorName)
            intent.putExtra("MENTOR_DESCRIPTION", mentorProfession)
            intent.putExtra("MENTOR_IMAGE_URL", mentorImageUrl)
            startActivity(intent)
        }

        joinCommunityButton.setOnClickListener {
            // Pass the image URL to Chat activity
            val chatIntent = Intent(this, Chat::class.java)
            chatIntent.putExtra("COMMUNITY_MEMBER_IMAGE_URL", mentorImageUrl)
            chatIntent.putExtra("MENTOR_NAME", mentorName)
            startActivity(chatIntent)
        }
    }
}
