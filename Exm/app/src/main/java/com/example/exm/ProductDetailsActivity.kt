package com.example.exm

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProductDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        val name = intent.getStringExtra("productName")
        val price = intent.getIntExtra("productPrice", 0)

        val tvDetails = findViewById<TextView>(R.id.tvProductDetails)
        tvDetails.text = "You selected: $name\nPrice: ₹$price"

        Toast.makeText(this, "Proceed to checkout...", Toast.LENGTH_SHORT).show()
    }
}
