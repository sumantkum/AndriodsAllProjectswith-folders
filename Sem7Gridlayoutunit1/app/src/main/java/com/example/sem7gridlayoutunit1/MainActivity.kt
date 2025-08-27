package com.example.sem7gridlayoutunit1

import android.os.Bundle
import android.widget.GridView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val gridView: GridView = findViewById(R.id.grid_view)
        val items = listOf(
            GridItem(R.drawable.img, "Image 1"),
            GridItem(R.drawable.img_1, "Image 2"),
            GridItem(R.drawable.img_2, "Image 3"),
            GridItem(R.drawable.img_3, "Image 4"),
            GridItem(R.drawable.img_4, "Image 5"),

        )
        val adapter = GridAdapter(this, items)
        gridView.adapter = adapter

        gridView.setOnItemClickListener { _, _, position, _->
            val selectedItem = items[position]
            Toast.makeText(this, "This is selected Item: ${selectedItem}", Toast.LENGTH_SHORT).show()
        }


    }
}