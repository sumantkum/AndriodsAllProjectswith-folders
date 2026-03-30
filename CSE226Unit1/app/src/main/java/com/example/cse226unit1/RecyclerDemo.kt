package com.example.cse226unit1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerDemo : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerContactAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recycler_demo)
        recyclerView = findViewById(R.id.recyclerViewContacts)

        val contacts = listOf(
            Contact("Amit Sharma", "+91-99999999"),
            Contact("Riya Mehta", "+91-888888888"),
            Contact("John Doe", "+91-777777777"),
            Contact("Neha Singh", "+91-666666666")
        )

        adapter = RecyclerContactAdapter(this, contacts)

        // Set Layout Manager (Linear)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set Adapter
        recyclerView.adapter = adapter
    }
}