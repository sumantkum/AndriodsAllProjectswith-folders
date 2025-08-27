package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RatingBar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rating_bar)

        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val submitButton = findViewById<Button>(R.id.button)
        val textView = findViewById<TextView>(R.id.textView)

        submitButton.setOnClickListener {
            val rating = ratingBar.rating
            textView.text="you Rated : $rating"
        }


    }
}