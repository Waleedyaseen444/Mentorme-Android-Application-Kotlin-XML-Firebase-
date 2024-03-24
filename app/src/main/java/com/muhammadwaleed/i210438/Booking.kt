package com.muhammadwaleed.i210438

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*

class Booking : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var selectedDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        val backButton = findViewById<ImageButton>(R.id.imageButton14)
        val bookAppointmentButton = findViewById<Button>(R.id.button5)
        val nameTextView = findViewById<TextView>(R.id.textView46)
        val professionTextView = findViewById<TextView>(R.id.textView63)
        val mentorImageView = findViewById<CircleImageView>(R.id.textView12)
        val calendarView = findViewById<CalendarView>(R.id.calendarView)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Receive mentor data from intent
        val mentorName = intent.getStringExtra("MENTOR_NAME")
        val mentorProfession = intent.getStringExtra("MENTOR_DESCRIPTION")
        val mentorImageUrl = intent.getStringExtra("MENTOR_IMAGE_URL")

        val image2 = findViewById<ImageButton>(R.id.imageButton2)


        image2.setOnClickListener {
            val user = auth.currentUser
            if (user != null) {
                val intent = Intent(this@Booking, Chatopen::class.java)
                intent.putExtra("MENTOR_NAME", mentorName)
                startActivity(intent)
            } else {
                // User is not signed in, handle this case as needed
            }
        }
        // Set received mentor data to TextViews
        nameTextView.text = mentorName
        professionTextView.text = mentorProfession

        // Load mentor image using Picasso
        if (!mentorImageUrl.isNullOrEmpty()) {
            Picasso.get().load(mentorImageUrl).into(mentorImageView)
        }

        backButton.setOnClickListener {
            onBackPressed()
        }

        // On calendar date change, update the selected date
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            selectedDate = sdf.format(calendar.time)
        }

        // On button click, save booking details to Firebase Realtime Database
        bookAppointmentButton.setOnClickListener {
            val user = auth.currentUser
            if (user != null) {
                if (selectedDate.isNullOrEmpty()) {
                    Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Get current time
                val calendar = Calendar.getInstance()
                val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)

                // Create a reference to the Firebase database
                val database = FirebaseDatabase.getInstance()
                val userId = user.uid
                val myRef = database.getReference("users/$userId/booked_sessions")

                // Generate a unique key for the session
                val sessionKey = myRef.push().key

                // Create a Session object
                val session = SessionDetails(
                    mentorName ?: "",
                    mentorProfession ?: "",
                    selectedDate,
                    currentTime,
                    mentorImageUrl ?: ""
                )

                // Save the session to the database under the generated key
                if (sessionKey != null) {
                    myRef.child(sessionKey).setValue(session)
                        .addOnSuccessListener {

                            val notificationRef = FirebaseDatabase.getInstance().reference.child("notifications").push()
                            val notificationMessage = "Booking made for mentor: $mentorName"
                            val notification = mapOf(
                                "message" to notificationMessage,
                                "timestamp" to ServerValue.TIMESTAMP
                            )
                            notificationRef.setValue(notification)
                            Toast.makeText(this, "Session Booked Successfully!", Toast.LENGTH_SHORT).show()
                            val myFirebaseMessagingService = MyFirebaseMessagingService()
                            myFirebaseMessagingService.generateNotification(this,"Mentor me", " Session Booked Successfully! " )
                            // After booking, navigate to Bookedsession activity
                            startActivity(intent)
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Failed to book session.", Toast.LENGTH_SHORT).show()
                        }
                }
            } else {
                // User is not signed in, handle this case as needed
            }
        }
    }
}