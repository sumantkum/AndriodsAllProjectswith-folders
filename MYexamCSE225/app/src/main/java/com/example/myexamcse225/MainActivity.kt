package com.example.myexamcse225

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar);
        setSupportActionBar(toolbar);

        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        ratingBar.setOnRatingBarChangeListener {
            _, rating, _ ->
            Toast.makeText(this, "Rating: $rating", Toast.LENGTH_LONG).show()
        }

        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        progressBar.progress = 70


        val btnToast = findViewById<Button>(R.id.btnToast)
        btnToast.setOnClickListener {
            Toast.makeText(this, "Custom Toast", Toast.LENGTH_LONG).show()
        }

    }
}