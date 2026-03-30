package com.example.myca1

import android.R
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
            Product(R.drawable.product1, "Product 1", "$10", "This is product 1 description."),
            Product(R.drawable.product2, "Product 2", "$20", "This is product 2 description."),
            Product(R.drawable.product3, "Product 3", "$30", "This is product 3 description."),
            Product(R.drawable.product4, "Product 4", "$40", "This is product 4 description.")
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2) // 2 columns
        recyclerView.adapter = ProductAdapter(products)
    }
}




