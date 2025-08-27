package com.example.electricvehiclesapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat



class CarActivity : AppCompatActivity() {

    lateinit var carListView: ListView
    val carNames = arrayOf("Car 1", "Car 2", "Car 3", "Car 4", "Car 5")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car)

        carListView = findViewById(R.id.carListView)

        // Set up the adapter to display the car names
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, carNames)
        carListView.adapter = adapter
    }
}
