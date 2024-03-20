package com.muhammadwaleed.i210438

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream
import java.util.*

class Communitychat : AppCompatActivity() {

    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var editTextMessage: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var fileButton: ImageButton
    private lateinit var imageUpload: ImageButton
    private lateinit var voicemessage: ImageButton
    private lateinit var camerabutton: ImageButton
    private lateinit var callbutton: ImageButton
    private lateinit var videocallbutton: ImageButton

    private lateinit var auth: FirebaseAuth
    private lateinit var messagesReference: DatabaseReference
    private lateinit var storageRef: StorageReference

    private val IMAGE_PICK_CODE = 1000
    private val FILE_PICK_CODE = 1001
    private val CAMERA_REQUEST_CODE = 1002
    private val messagesList = mutableListOf<Message>()
    private val offlineMessages = mutableListOf<Message>()

    private lateinit var audioRecorder: AudioRecorder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_communitychat)

        auth = FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance()
        val currentUser = auth.currentUser
        val currentUserId = currentUser?.uid

        val mentorName = intent.getStringExtra("MENTOR_NAME")
        messagesReference = database.reference.child("community_messages_$mentorName")
        storageRef = FirebaseStorage.getInstance().reference

        audioRecorder = AudioRecorder()

        // Initialize views
        val imageViewCommunity = findViewById<CircleImageView>(R.id.textView12)
        val textViewName = findViewById<TextView>(R.id.textView26)

        // Load community member's image using Picasso
        val imageUrl = intent.getStringExtra("COMMUNITY_MEMBER_IMAGE_URL")
        if (!imageUrl.isNullOrEmpty()) {
            Picasso.get().load(imageUrl).into(imageViewCommunity)
        }

        // Set community member's name
        textViewName.text = mentorName

        // Setup RecyclerView and Adapter
        messageAdapter = MessageAdapter(messagesList, object : MessageAdapter.OnMessageClickListener {
            override fun onMessageClick(position: Int) {
                // Handle message click
            }

            override fun onMessageLongClick(position: Int) {
                val message = messagesList[position]
                val options = arrayOf("Edit", "Delete")
                val builder = AlertDialog.Builder(this@Communitychat)
                builder.setItems(options) { _, which ->
                    when (which) {
                        0 -> {
                            // Edit selected
                            val editText = EditText(this@Communitychat)
                            editText.setText(message.messageText)
                            val dialogBuilder = AlertDialog.Builder(this@Communitychat)
                                .setTitle("Edit Message")
                                .setView(editText)
                                .setPositiveButton("Save") { _, _ ->
                                    val newText = editText.text.toString().trim()
                                    if (newText.isNotEmpty() && newText != message.messageText) {
                                        editMessage(message, newText)
                                    }
                                }
                                .setNegativeButton("Cancel") { dialog, _ ->
                                    dialog.dismiss()
                                }
                            val dialog = dialogBuilder.create()
                            dialog.show()
                        }
                        1 -> {
                            // Delete selected
                            deleteMessage(message)
                        }
                    }
                }
                val dialog = builder.create()
                dialog.show()
            }
        })

        messageRecyclerView = findViewById(R.id.messageRecyclerView)
        editTextMessage = findViewById(R.id.editTextTextMultiLine3)
        sendButton = findViewById(R.id.imageButton20)
        fileButton = findViewById(R.id.imageButton21)
        imageUpload = findViewById(R.id.imageButton19)
        voicemessage = findViewById(R.id.imageButton18)
        camerabutton = findViewById(R.id.imageButton13)
        callbutton = findViewById(R.id.imageButton16)
        videocallbutton = findViewById(R.id.imageButton17)

        messageRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@Communitychat)
            adapter = messageAdapter
            addItemDecoration(ItemOffsetDecoration(16)) // Adding item decoration with 16dp offset
        }

        // Send message button click listener
        sendButton.setOnClickListener {
            val messageText = editTextMessage.text.toString().trim()
            if (messageText.isNotEmpty()) {
                sendMessage(messageText)
            }
        }

        // File button click listener
        fileButton.setOnClickListener {
            pickFileFromDevice()
        }

        // Image upload button click listener
        imageUpload.setOnClickListener {
            pickImageFromGallery()
        }

        // Camera button click listener
        camerabutton.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                openCamera()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_REQUEST_CODE
                )
            }
        }

        // Voice message button click listener
        voicemessage.setOnClickListener {
            if (audioRecorder.isRecording) {
                audioRecorder.stopRecording { audioUri ->
                    if (audioUri != null) {
                        uploadAudioToFirebase(audioUri)
                    } else {
                        // Handle audio recording failure
                        Toast.makeText(this, "Failed to record audio", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                audioRecorder.startRecording()
                Toast.makeText(this, "Recording started", Toast.LENGTH_SHORT).show()
            }
        }

        // Listen for new messages
        messagesReference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(Message::class.java)
                if (message != null) {
                    messagesList.add(message)
                    messageAdapter.notifyItemInserted(messagesList.size - 1)
                    scrollToBottom()
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val updatedMessage = snapshot.getValue(Message::class.java)
                if (updatedMessage != null) {
                    val existingMessage = messagesList.find { it.userId == updatedMessage.userId }
                    if (existingMessage != null) {
                        val index = messagesList.indexOf(existingMessage)
                        messagesList[index] = updatedMessage
                        messageAdapter.notifyItemChanged(index)
                    }
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val removedMessage = snapshot.getValue(Message::class.java)
                if (removedMessage != null) {
                    val existingMessage = messagesList.find { it.userId == removedMessage.userId }
                    if (existingMessage != null) {
                        val index = messagesList.indexOf(existingMessage)
                        messagesList.removeAt(index)
                        messageAdapter.notifyItemRemoved(index)
                    }
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}
        })

        // Check for internet connectivity and sync offline messages
        if (isNetworkAvailable()) {
            syncOfflineMessages()
        } else {
            Toast.makeText(this, "No internet connection. Offline mode.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendMessage(messageText: String) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val message = Message(userId, messageText, System.currentTimeMillis(), null, null, null)
            messagesReference.push().setValue(message)
                .addOnSuccessListener {
                    editTextMessage.text.clear()
                    scrollToBottom()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to send message", Toast.LENGTH_SHORT).show()
                    offlineMessages.add(message)
                }
        }
    }

    private fun pickFileFromDevice() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(intent, FILE_PICK_CODE)
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    private fun uploadFileToFirebase(fileUri: Uri) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val fileRef = storageRef.child("community_files_$userId/${UUID.randomUUID()}")
            val uploadTask = fileRef.putFile(fileUri)
            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                fileRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    val message = Message(userId, "", System.currentTimeMillis(), downloadUri.toString(), null, null)
                    messagesReference.push().setValue(message)
                } else {
                    Toast.makeText(this, "Failed to upload file", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun uploadImageToFirebase(imageUri: Uri) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val imageRef = storageRef.child("community_images_$userId/${UUID.randomUUID()}")
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
                    val message = Message(userId, "", System.currentTimeMillis(), null, downloadUri.toString(), null)
                    messagesReference.push().setValue(message)
                } else {
                    Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun uploadAudioToFirebase(audioUri: Uri) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val audioRef = storageRef.child("community_audio_$userId/${UUID.randomUUID()}")
            val uploadTask = audioRef.putFile(audioUri)
            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                audioRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    val message = Message(userId, "", System.currentTimeMillis(), null, null, downloadUri.toString())
                    messagesReference.push().setValue(message)
                } else {
                    Toast.makeText(this, "Failed to upload audio", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun editMessage(message: Message, newText: String) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val updatedMessage = message.copy(messageText = newText)
            val messageKey = messagesList.indexOf(message)
            messagesReference.child(messageKey.toString()).setValue(updatedMessage)
                .addOnSuccessListener {
                    messagesList[messageKey] = updatedMessage
                    messageAdapter.notifyItemChanged(messageKey)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to edit message", Toast.LENGTH_SHORT).show()
                }
        }
    }


    private fun deleteMessage(message: Message) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val messageKey = messagesList.indexOf(message)
            messagesReference.child(messageKey.toString()).removeValue()
                .addOnSuccessListener {
                    messagesList.removeAt(messageKey)
                    messageAdapter.notifyItemRemoved(messageKey)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to delete message", Toast.LENGTH_SHORT).show()
                }
        }
    }



    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network: Network? = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            return capabilities != null && (
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
        }
        return false
    }

    private fun syncOfflineMessages() {
        for (message in offlineMessages) {
            messagesReference.push().setValue(message)
                .addOnSuccessListener {
                    offlineMessages.remove(message)
                    Log.d("OfflineSync", "Message sent: $message")
                }
                .addOnFailureListener {
                    Log.e("OfflineSync", "Failed to send message: $message")
                }
        }
    }

    private fun scrollToBottom() {
        messageRecyclerView.scrollToPosition(messagesList.size - 1)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                IMAGE_PICK_CODE -> {
                    val imageUri = data?.data
                    if (imageUri != null) {
                        uploadImageToFirebase(imageUri)
                    } else {
                        Toast.makeText(this, "Failed to retrieve image", Toast.LENGTH_SHORT).show()
                    }
                }
                FILE_PICK_CODE -> {
                    val fileUri = data?.data
                    if (fileUri != null) {
                        uploadFileToFirebase(fileUri)
                    } else {
                        Toast.makeText(this, "Failed to retrieve file", Toast.LENGTH_SHORT).show()
                    }
                }
                CAMERA_REQUEST_CODE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap?
                    if (imageBitmap != null) {
                        val imageUri = getImageUri(imageBitmap)
                        uploadImageToFirebase(imageUri)
                    } else {
                        Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun getImageUri(imageBitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(contentResolver, imageBitmap, "Title", null)
        return Uri.parse(path)
    }

    override fun onDestroy() {
        super.onDestroy()
        audioRecorder.cancelRecording()
    }
}
