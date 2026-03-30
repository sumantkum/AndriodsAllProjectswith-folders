package com.example.cse226unit1

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CardViewDemo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_card_view_demo)
        // Accessing CardView and Button
        val cardView = findViewById<CardView>(R.id.cardView)
        val actionButton = findViewById<Button>(R.id.actionButton)

        // Click Listener for Button inside Card
        actionButton.setOnClickListener {
            Toast.makeText(this, "Button Clicked!", Toast.LENGTH_SHORT).show()
        }

        // Click Listener for Entire Card
        cardView.setOnClickListener {
            Toast.makeText(this, "Card Clicked!", Toast.LENGTH_SHORT).show()
        }
    }
}