package com.example.electricvehiclesapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class BikeActivity : AppCompatActivity() {

    lateinit var bikeListView: ListView
    val bikeNames = arrayOf("Bike 1", "Bike 2", "Bike 3", "Bike 4", "Bike 5")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bike)

        bikeListView = findViewById(R.id.bikeListView)

        // Set up the adapter to display the bike names
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, bikeNames)
        bikeListView.adapter = adapter
    }
}
