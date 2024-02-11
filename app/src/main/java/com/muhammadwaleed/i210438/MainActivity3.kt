package com.muhammadwaleed.i210438

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity3 : AppCompatActivity() {

    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var categoriesRecyclerView: RecyclerView
    private lateinit var topMentorsRecyclerView: RecyclerView
    private lateinit var mentorHomeAdapter: MentorHomeAdapter
    private lateinit var educationMentorsRecyclerView: RecyclerView
    private lateinit var educationMentorAdapter: EducationMentorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        val notificationBtn=findViewById<ImageButton>(R.id.btnNotifications)
        notificationBtn.setOnClickListener {
            val intent = Intent(this, Notifications::class.java)
            startActivity(intent)
        }
        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView)
        categoriesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val categories = listOf(
            Category(1, "All", isSelected = true),
            Category(2, "Education"),
            Category(3, "Entrepreneurship"),
            Category(4, "Personal Growth"),
            Category(5, "Career")
        )
        categoriesAdapter = CategoriesAdapter(categories) {  }
        categoriesRecyclerView.adapter = categoriesAdapter

        topMentorsRecyclerView = findViewById(R.id.topMentorsRecyclerView)
        topMentorsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val topMentors = listOf(
            Mentorhome("John Cooper", "UX Designer @ Google", "Available", "$99/session", true),
            Mentorhome("Martina Watson", "Lead Technology Officer", "Not Available", "$150/session", false),
            Mentorhome("Emma", "Lead Officer", "Not Available", "$150/session", false)

        )
        mentorHomeAdapter = MentorHomeAdapter(topMentors) {
            val intent = Intent(this@MainActivity3, Mentordetails::class.java)
            startActivity(intent)
        }
        topMentorsRecyclerView.adapter = mentorHomeAdapter

        educationMentorsRecyclerView = findViewById(R.id.educationMentorsRecyclerView)
        educationMentorsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val educationMentors = listOf(
            EducationMentor("Jane Smith", "Mathematics Educator", "Available", "$80/session", false),
            EducationMentor("Emma Johnson", "Science Tutor", "Available", "$85/session", true),
            EducationMentor("Ethan Brown", "History Professor", "Available", "$90/session", true)
        )
        educationMentorAdapter = EducationMentorAdapter(educationMentors) {
            val intent = Intent(this@MainActivity3, Mentordetails::class.java)
            startActivity(intent)
        }
        educationMentorsRecyclerView.adapter = educationMentorAdapter

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
    }
}
