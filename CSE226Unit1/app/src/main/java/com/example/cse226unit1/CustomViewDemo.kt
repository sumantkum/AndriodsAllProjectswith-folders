package com.example.cse226unit1

import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CustomViewDemo : AppCompatActivity() {
    lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_custom_view_demo)
        listView = findViewById(R.id.listViewContacts)

        val contacts = listOf(
            Contact("Amit Sharma", "9999999999"),
            Contact("Riya Mehta", "8888888888"),
            Contact("John Doe", "7777777777")
        )

        val adapter = ContactAdapter(this,contacts)
        listView.adapter = adapter
    }
}