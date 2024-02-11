package com.muhammadwaleed.i210438

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class Searchresults : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchresults)

        val BackBtn=findViewById<ImageButton>(R.id.backBtn)
        BackBtn.setOnClickListener{
            val intent = Intent(this, Search::class.java)
            startActivity(intent)
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dataList = createDataList()
        val adapter = RecentAdapter(dataList)
        recyclerView.adapter = adapter

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
                    true
                }
                else -> false
            }
        }
    }




    private fun createDataList(): List<Recentdata> {

        return listOf(
            Recentdata("Grorge", "UI designer", "Available", "$50", R.drawable.img1, R.drawable.hearts),
            Recentdata("Sam 2", "Lead technology officer", "Available", "$60", R.drawable.img2, R.drawable.hearts),
            Recentdata("Michael 3", "Lead technology officer", "Not Available", "$60", R.drawable.img3, R.drawable.ic_heart_outline),
            Recentdata("Henry 4", "Lead technology officer", "Available", "$60", R.drawable.img4, R.drawable.hearts),
            Recentdata("Jack 5", "Lead technology officer", "Not Available", "$60", R.drawable.img5, R.drawable.ic_heart_outline),

            )
    }
}
