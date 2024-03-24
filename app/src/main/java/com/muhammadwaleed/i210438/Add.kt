package com.muhammadwaleed.i210438

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class Add : AppCompatActivity() {

    private lateinit var storage: FirebaseStorage
    private lateinit var storageRef: StorageReference
    private lateinit var database: DatabaseReference

    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference.child("mentor_images")
        database = FirebaseDatabase.getInstance().reference.child("mentors")

        val updateBtn = findViewById<Button>(R.id.btnUpload)
        val cameraButton: ImageButton = findViewById(R.id.imageButton)
        val videoButton: ImageButton = findViewById(R.id.imageButton2)
        val updateEducation = findViewById<Button>(R.id.button2)

        cameraButton.setOnClickListener {
            openGalleryForImage()
        }

        videoButton.setOnClickListener {
            openGalleryForImage()
        }

        updateBtn.setOnClickListener {
            val nameInput = findViewById<EditText>(R.id.editTextText).text.toString()
            val descriptionInput =
                findViewById<EditText>(R.id.editTextTextMultiLine2).text.toString()
            val availabilityInput = findViewById<EditText>(R.id.editTextText2).text.toString()

            if (selectedImageUri != null) {
                uploadImageAndMentor(nameInput, descriptionInput, availabilityInput, false)
            } else {
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
            }
        }

        updateEducation.setOnClickListener {
            val nameInput = findViewById<EditText>(R.id.editTextText).text.toString()
            val descriptionInput =
                findViewById<EditText>(R.id.editTextTextMultiLine2).text.toString()
            val availabilityInput = findViewById<EditText>(R.id.editTextText2).text.toString()

            if (selectedImageUri != null) {
                uploadImageAndMentor(nameInput, descriptionInput, availabilityInput, true)
            } else {
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                selectedImageUri = data?.data
            }
        }

    private fun uploadImageAndMentor(
        name: String,
        description: String,
        availability: String,
        isEducationMentor: Boolean
    ) {
        selectedImageUri?.let { imageUri ->
            val imageRef = storageRef.child("${System.currentTimeMillis()}.jpg")
            val uploadTask = imageRef.putFile(imageUri)

            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                imageRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    val imageUrl = downloadUri.toString()

                    val newMentor = Mentorhome(
                        name,
                        description,
                        availability,
                        "$99/session",
                        true,
                        imageUrl
                    )

                    val mentorRef = if (isEducationMentor) {
                        FirebaseDatabase.getInstance().reference.child("education_mentors")
                    } else {
                        FirebaseDatabase.getInstance().reference.child("mentors")
                    }

                    mentorRef.push().setValue(newMentor)


                    Toast.makeText(
                        this,
                        "Mentor added successfully to ${if (isEducationMentor) "Education Mentors" else "Top Mentors"}",
                        Toast.LENGTH_SHORT
                    ).show()
                    val myFirebaseMessagingService = MyFirebaseMessagingService()
                    myFirebaseMessagingService.generateNotification(this,"Mentor me", "Mentor added Successfully  " )
                    finish()
                } else {
                    Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    companion object {
        const val EXTRA_NAME = "com.muhammadwaleed.i210438.EXTRA_NAME"
        const val EXTRA_DESCRIPTION = "com.muhammadwaleed.i210438.EXTRA_DESCRIPTION"
        const val EXTRA_AVAILABILITY = "com.muhammadwaleed.i210438.EXTRA_AVAILABILITY"
    }
}
