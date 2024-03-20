package com.muhammadwaleed.i210438

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Editprofile : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var userEmail: String
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var cityEditText: EditText
    private lateinit var countryEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editprofile)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        nameEditText = findViewById(R.id.editTextText3)
        emailEditText = findViewById(R.id.Email)
        cityEditText = findViewById(R.id.idCity)
        countryEditText = findViewById(R.id.idCountry)
        passwordEditText = findViewById(R.id.editTextText7)

        val updateBtn = findViewById<Button>(R.id.IdUpdate)
        updateBtn.setOnClickListener {
            updateProfile()
        }

        val back = findViewById<ImageButton>(R.id.backbutton)
        back.setOnClickListener {
            onBackPressed()
        }

        // Get the logged-in user's email
        userEmail = auth.currentUser?.email ?: ""
        // Load user data into EditText fields
        loadUserData()
    }

    private fun loadUserData() {
        val userRef: DatabaseReference = database.reference.child("Users").child(userEmail.replace(".", ","))
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = snapshot.getValue(Signupuser::class.java)
                    if (user != null) {
                        nameEditText.setText(user.name)
                        emailEditText.setText(user.email)
                        cityEditText.setText(user.city)
                        countryEditText.setText(user.country)
                        passwordEditText.setText(user.password)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                Toast.makeText(this@Editprofile, "Failed to load user data.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateProfile() {
        val name = nameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val city = cityEditText.text.toString().trim()
        val country = countryEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        val userRef: DatabaseReference = database.reference.child("Users").child(userEmail.replace(".", ","))
        val updatedUser = Signupuser(name, email, "", country, city, password)

        userRef.setValue(updatedUser)
            .addOnSuccessListener {
                Toast.makeText(this, "Profile updated successfully.", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to update profile.", Toast.LENGTH_SHORT).show()
            }
    }
}
