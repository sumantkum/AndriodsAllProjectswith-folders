package com.example.myca1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Static product list
        val products = listOf(
            Product(R.drawable.img, "Product 1", "$10", "This is product 1 description."),
            Product(R.drawable.img, "Product 2", "$20", "This is product 2 description."),
            Product(R.drawable.img, "Product 3", "$30", "This is product 3 description."),
            Product(R.drawable.img, "Product 4", "$40", "This is product 4 description.")
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2) // 2 columns
        recyclerView.adapter = ProductAdapter(products)
    }
}
