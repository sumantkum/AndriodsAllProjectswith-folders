package com.example.sem7listviewunit1

import android.os.Bundle
import android.widget.ListView
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

        val listView: ListView = findViewById(R.id.listView)

        val fruitList = listOf(
            Fruit("Apple", R.drawable.apple),
            Fruit("Mango", R.drawable.mango),
            Fruit("Banana", R.drawable.banana),
            Fruit("Orange", R.drawable.orange),
            Fruit("Kivi", R.drawable.kivi),

            )

        val adapter = FruitAdapter(this, fruitList)
        listView.adapter = adapter

        listView.setOnItemClickListener{
            _, _, position, _ ->
            val selectedFruit = fruitList[position].name
            Toast.makeText(this, "You selected: $selectedFruit", Toast.LENGTH_SHORT).show()
        }

    }
}