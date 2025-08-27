package com.example.exm

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val img1 = findViewById<ImageView>(R.id.ivProduct1)
        val img2 = findViewById<ImageView>(R.id.ivProduct2)
        val img3 = findViewById<ImageView>(R.id.ivProduct3)


        img1.setOnClickListener {
            navigateToProduct("Product 1", 500)
        }

        img2.setOnClickListener {
            navigateToProduct("Product 2", 300)
        }

        img3.setOnClickListener {
            navigateToProduct("Product 3", 600)
        }
    }


    private fun navigateToProduct(name: String, price: Int) {
        val intent = Intent(this, ProductDetailsActivity::class.java).apply {
            putExtra("productName", name)
            putExtra("productPrice", price)
        }
        startActivity(intent)
        Toast.makeText(this, "$name clicked", Toast.LENGTH_SHORT).show()
    }

}
