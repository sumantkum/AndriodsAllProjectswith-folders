package com.example.unit3app

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.viewpager2example.PageAdapter

class ViewPager : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var prevButton: Button
    private lateinit var nextButton: Button
    private lateinit var adapter: PageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)
        prevButton = findViewById(R.id.prevButton)
        nextButton = findViewById(R.id.nextButton)

        val pages = listOf(
            "Hello, I am Sumant Kumar Pandit.",
            "I'm from Bihar and currently pursuing B.Tech at LPU.",
            "I love coding and I’m very curious to learn new things!",
            "Thank you for going through my pages!"
        )

        adapter = PageAdapter(pages)
        viewPager.adapter = adapter

        prevButton.setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem > 0) {
                viewPager.currentItem = currentItem - 1
            }
        }

        nextButton.setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem < adapter.itemCount - 1) {
                viewPager.currentItem = currentItem + 1
            }
        }
    }
}