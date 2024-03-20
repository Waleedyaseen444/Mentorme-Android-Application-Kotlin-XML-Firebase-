package com.muhammadwaleed.i210438

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class Signup : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth // Declare FirebaseAuth variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        auth = FirebaseAuth.getInstance()

        val loginTextView: TextView = findViewById(R.id.login)
        loginTextView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val email = findViewById<EditText>(R.id.idEmailAddress)
        val password = findViewById<EditText>(R.id.editTextNumberPassword)
        val nameEditText = findViewById<EditText>(R.id.id_name)
        val phoneEditText = findViewById<EditText>(R.id.editTextPhone)
        val countryEditText = findViewById<EditText>(R.id.Country)
        val cities = findViewById<EditText>(R.id.editTextCity)

        val signupBtn = findViewById<Button>(R.id.signup)
        signupBtn.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val userEmail = email.text.toString().trim()
            val phone = phoneEditText.text.toString().trim()
            val country = countryEditText.text.toString().trim()
            val citywide = cities.text.toString().trim()
            val userPass = password.text.toString().trim()


            if (userEmail.isEmpty() || userPass.isEmpty() || name.isEmpty() || phone.isEmpty() || country.isEmpty() || citywide.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                val signupUser = Signupuser(name, userEmail, phone, country, citywide,  userPass )
                userSignUp(userEmail, userPass, signupUser)
            }
        }
    }

    private fun userSignUp(email: String, password: String, signupUser: Signupuser) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser
                    // Call the submitData function here, after successful sign up
                    submitData(signupUser)
                    Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show()
                } else {
                    // If sign up fails due to existing email, show error message
                    if (task.exception is FirebaseAuthUserCollisionException) {
                        Toast.makeText(this, "Email already in use", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Sign up failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun submitData(signupUser: Signupuser) {
        Log.d("Firebase", "Submitting data to database: $signupUser")
        val database = FirebaseDatabase.getInstance().reference
        database.child("Users").child(signupUser.email.replace(".", ",")).setValue(signupUser)
            .addOnSuccessListener {
                Toast.makeText(this@Signup, "User registered successfully", Toast.LENGTH_SHORT).show()
                Log.d("Firebase", "Data submitted successfully: $signupUser")
                val intent = Intent(this@Signup, MainActivity2::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this@Signup, "Failed to register user: ${it.message}", Toast.LENGTH_SHORT).show()
                Log.e("Firebase", "Failed to submit data: ${it.message}")
            }
    }
}