package com.example.sem7myfirstapp

import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listView);
        val data = listOf(
            Person("Anita", "Android Developer"),
            Person("Bharat", "iOS Developer"),
            Person("Chirag", "Backend Engineer"),
            Person("Deepa", "UI/UX Designer")
            )

        listView.adapter = PersonAdapter(this, data);
        listView.setOnItemClickListener{_, _, position, _ ->}



    }
}