package com.muhammadwaleed.i210438

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.android.material.bottomnavigation.BottomNavigationView
import de.hdodenhof.circleimageview.CircleImageView

class Profile : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var userEmail: String
    private lateinit var profileImageView: CircleImageView
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var reviewsAdapter: ReviewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        profileImageView = findViewById(R.id.textView12)

        val editProfileButton = findViewById<ImageButton>(R.id.imageButton3)
        editProfileButton.setOnClickListener {
            Log.d("ProfileActivity", "Edit Profile button clicked")
            val intent = Intent(this, Editprofile::class.java)
            startActivity(intent)
        }

        val backButton = findViewById<ImageButton>(R.id.imageButton9)
        backButton.setOnClickListener {
            Log.d("ProfileActivity", "Back button clicked")
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }

        val bookedSessionsButton: Button = findViewById(R.id.button7)
        bookedSessionsButton.setOnClickListener {
            Log.d("ProfileActivity", "Booked Sessions button clicked")
            val intent = Intent(this, Bookedsession::class.java)
            startActivity(intent)
        }

        val threeDotsButton = findViewById<ImageButton>(R.id.imageButton6)
        threeDotsButton.setOnClickListener {
            Log.d("ProfileActivity", "Three Dots button clicked")
            val intent = Intent(this, Notifications::class.java)
            startActivity(intent)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewFavouriteMentors)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val favoriteMentors = listOf(
            Mentor("John Cooper", "UX Designer", "Available", true),
            Mentor("Emma Phillips", "Lead Technology Officer", "Available", true),
            Mentor("Micheals", "UX developer", "Available", true),
            Mentor("Emma Phillips", "Lead Technology Officer", "Available", true)
        )

        val adapter = FavouriteMentorsAdapter(favoriteMentors)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)

        val reviewsRecyclerView = findViewById<RecyclerView>(R.id.recyclerViewReviews)
        reviewsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Initialize ReviewsAdapter
        reviewsAdapter = ReviewsAdapter(ArrayList())
        reviewsRecyclerView.adapter = reviewsAdapter
        reviewsRecyclerView.setHasFixedSize(true)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val intent = Intent(this, MainActivity3::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_add -> {
                    val intent = Intent(this, Add::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_chat -> {
                    val intent = Intent(this, Chat::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_profile -> {
                    val intent = Intent(this, Profile::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_search -> {
                    val intent = Intent(this, Search::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        // Get user email from intent
        userEmail = intent.getStringExtra("userEmail") ?: ""
        // Load user data
        loadUserData()

        // Update profile picture on button click
        val changeProfileButton = findViewById<ImageButton>(R.id.imageButton5)
        changeProfileButton.setOnClickListener {
            Log.d("ProfileActivity", "Change Profile button clicked")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        // Load reviews for the user
        loadReviews()
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 71
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            val filePath = data.data!!
            val ref = storageReference.child("images/${auth.currentUser?.uid}")
            ref.putFile(filePath)
                .addOnSuccessListener { taskSnapshot ->
                    ref.downloadUrl.addOnSuccessListener { uri ->
                        val profileImageUrl = uri.toString()
                        updateProfileImageUrl(profileImageUrl)
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("ProfileActivity", "Error uploading profile image: ${e.message}")
                }
        }
    }

    private fun loadReviews() {
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid

        if (uid != null) {
            val ref = database.getReference("Feedbacks").child(uid)
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val reviewList = mutableListOf<Review>()
                        for (reviewSnapshot in snapshot.children) {
                            val mentorName = reviewSnapshot.child("mentorName").value.toString()
                            val reviewText = reviewSnapshot.child("reviewText").value.toString()
                            val rating = reviewSnapshot.child("rating").value.toString().toFloat()
                            // Set mentorName as reviewerName
                            val review = Review(mentorName, reviewText, rating)
                            reviewList.add(review)
                        }
                        reviewsAdapter.setData(reviewList)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                    Log.e("ProfileActivity", "Failed to read reviews: ${error.message}")
                }
            })
        }
    }

    private fun loadUserData() {
        val offlineName = sharedPreferences.getString("offlineName", "")
        val offlineCity = sharedPreferences.getString("offlineCity", "")

        if (offlineName != null && offlineCity != null && offlineName.isNotEmpty() && offlineCity.isNotEmpty()) {
            // Update UI with offline data
            updateUIOffline(offlineName, offlineCity)
        } else {
            val userRef: DatabaseReference = database.reference.child("Users").child(userEmail.replace(".", ","))
            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val user = snapshot.getValue(Signupuser::class.java)
                        if (user != null) {
                            // Save user data to SharedPreferences
                            with(sharedPreferences.edit()) {
                                putString("offlineName", user.name)
                                putString("offlineCity", user.city)
                                apply()
                            }
                            updateUI(user)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                    Log.e("ProfileActivity", "Failed to read user data: ${error.message}")
                }
            })
        }
    }

    private fun updateUI(user: Signupuser) {
        val nameTextView: TextView = findViewById(R.id.textView13)
        val cityTextView: TextView = findViewById(R.id.textView14)

        nameTextView.text = user.name
        cityTextView.text = user.city
    }

    private fun updateUIOffline(name: String, city: String) {
        val nameTextView: TextView = findViewById(R.id.textView13)
        val cityTextView: TextView = findViewById(R.id.textView14)

        nameTextView.text = name
        cityTextView.text = city
    }

    private fun updateProfileImageUrl(profileImageUrl: String) {
        val userRef = database.reference.child("Users").child(auth.currentUser?.email?.replace(".", ",") ?: "")
        userRef.child("profileImageUrl").setValue(profileImageUrl)
            .addOnSuccessListener {
                Log.d("ProfileActivity", "Profile image URL updated successfully")
                // Load the new image into CircleImageView
                Glide.with(this@Profile)
                    .load(profileImageUrl)
                    .into(profileImageView)
            }
            .addOnFailureListener { e ->
                Log.e("ProfileActivity", "Error updating profile image URL: ${e.message}")
            }
    }
}