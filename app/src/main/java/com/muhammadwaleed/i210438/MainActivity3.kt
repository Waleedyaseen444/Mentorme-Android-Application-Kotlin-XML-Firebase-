package com.muhammadwaleed.i210438

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileObserver
import android.util.Log
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*

class MainActivity3 : AppCompatActivity() {

    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var categoriesRecyclerView: RecyclerView
    private lateinit var topMentorsRecyclerView: RecyclerView
    private lateinit var mentorHomeAdapter: MentorHomeAdapter
    private lateinit var educationMentorsRecyclerView: RecyclerView
    private lateinit var educationMentorAdapter: EducationMentorAdapter
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        database = FirebaseDatabase.getInstance().reference.child("mentors")


        val notificationBtn = findViewById<ImageButton>(R.id.btnNotifications)
        notificationBtn.setOnClickListener {
            val intent = Intent(this, Notifications::class.java)
            startActivity(intent)
        }

        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView)
        categoriesRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val categories = listOf(
            Category(1, "All", isSelected = true),
            Category(2, "Education"),
            Category(3, "Entrepreneurship"),
            Category(4, "Personal Growth"),
            Category(5, "Career")
        )
        categoriesAdapter = CategoriesAdapter(categories) { }
        categoriesRecyclerView.adapter = categoriesAdapter

        topMentorsRecyclerView = findViewById(R.id.topMentorsRecyclerView)
        topMentorsRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Fetch and display top mentors
        fetchMentors()

        educationMentorsRecyclerView = findViewById(R.id.educationMentorsRecyclerView)
        educationMentorsRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Fetch and display education mentors
        fetchEducationMentors()

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Do nothing, already on home
                    true
                }
                R.id.navigation_add -> {
                    val intent = Intent(this, Add::class.java)
                    startActivityForResult(intent, ADD_MENTOR_REQUEST)
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
    }

    private fun fetchMentors() {
        val mentors: MutableList<Mentorhome> = mutableListOf()
        val query: Query = database

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mentors.clear()
                for (dataSnapshot in snapshot.children) {
                    val mentor = dataSnapshot.getValue(Mentorhome::class.java)
                    mentor?.let {
                        mentors.add(it)
                    }
                }
                updateMentorRecyclerView(mentors)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

    private fun updateMentorRecyclerView(mentors: List<Mentorhome>) {
        mentorHomeAdapter = MentorHomeAdapter(mentors) { mentor ->
            val intent = Intent(this@MainActivity3, Mentordetails::class.java)
            intent.putExtra("MENTOR_NAME", mentor.name)
            intent.putExtra("MENTOR_DESCRIPTION", mentor.occupation)
            intent.putExtra("MENTOR_IMAGE_URL", mentor.imageUrl) // Add image URL
            startActivity(intent)
        }


        topMentorsRecyclerView.adapter = mentorHomeAdapter
    }

    private fun fetchEducationMentors() {
        val educationMentors: MutableList<EducationMentor> = mutableListOf()
        val query: Query = FirebaseDatabase.getInstance().reference.child("education_mentors")

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                educationMentors.clear()
                for (dataSnapshot in snapshot.children) {
                    val mentor = dataSnapshot.getValue(EducationMentor::class.java)
                    mentor?.let {
                        educationMentors.add(it)
                    }
                }
                updateEducationMentorRecyclerView(educationMentors)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

    private fun updateEducationMentorRecyclerView(educationMentors: List<EducationMentor>) {
        educationMentorAdapter = EducationMentorAdapter(educationMentors) { mentor ->
            val intent = Intent(this@MainActivity3, Mentordetails::class.java)
            intent.putExtra("MENTOR_NAME", mentor.name)
            intent.putExtra("MENTOR_DESCRIPTION", mentor.profession)
            // No need to pass image URL for education mentors as they are not shown in this activity
            startActivity(intent)
        }

        educationMentorsRecyclerView.adapter = educationMentorAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_MENTOR_REQUEST && resultCode == RESULT_OK) {
            // Get mentor data from Add activity result
            val name = data?.getStringExtra(Add.EXTRA_NAME) ?: ""
            val description = data?.getStringExtra(Add.EXTRA_DESCRIPTION) ?: ""
            val availability = data?.getStringExtra(Add.EXTRA_AVAILABILITY) ?: ""

            // Create a new Mentorhome object with hardcoded price and favorite status
            val newMentor = Mentorhome(name, description, availability, "$99/session", true, "")

            // Add the new mentor to Firebase
            val key = database.push().key
            key?.let {
                database.child(it).setValue(newMentor)
            }

            // Notify the adapter of the change
            mentorHomeAdapter.notifyDataSetChanged()
        }
    }

    companion object {
        private const val ADD_MENTOR_REQUEST = 1
    }
}  