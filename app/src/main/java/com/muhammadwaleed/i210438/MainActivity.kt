package com.muhammadwaleed.i210438
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Handler().postDelayed({
            val loginIntent = Intent(this, MainActivity2::class.java)
            startActivity(loginIntent)
            finish()
        }, 3500)
    }
}


