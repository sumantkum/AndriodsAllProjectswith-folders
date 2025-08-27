package com.example.myapplicationsecond

import android.os.Bundle
import android.widget.RatingBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Find the RatingBars by their IDs
        val ratingBar1 = findViewById<RatingBar>(R.id.ratingBar1)
        val ratingBar2 = findViewById<RatingBar>(R.id.ratingBar2)

        // Set up a listener for the first RatingBar
        ratingBar1.setOnRatingBarChangeListener { _, rating, fromUser  ->

            if (fromUser ) {
                Toast.makeText(this, "You rated Image 1: $rating", Toast.LENGTH_SHORT).show()
            }
        }

        // Set up a listener for the second RatingBar
        ratingBar2.setOnRatingBarChangeListener { _, rating, fromUser  ->

            if (fromUser ) {
                Toast.makeText(this, "You rated Image 2: $rating", Toast.LENGTH_SHORT).show()
            }
        }
    }
}