package com.example.electricvehiclesapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btnBikes: Button = findViewById(R.id.btnBikes)
        val btnCars: Button = findViewById(R.id.btnCars)


        btnBikes.setOnClickListener {

            val intent = Intent(this, BikeActivity::class.java)
            startActivity(intent)
        }

        btnCars.setOnClickListener {

            val intent = Intent(this, CarActivity::class.java)
            startActivity(intent)
        }
    }
}
