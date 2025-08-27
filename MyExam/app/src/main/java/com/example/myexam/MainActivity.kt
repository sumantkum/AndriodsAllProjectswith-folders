package com.example.myexam

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val cartList = arrayListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val products = listOf(
            Product("Apple", 50, R.drawable.apple),
            Product("Carrot", 30, R.drawable.carrot),
            Product("Milk", 40, R.drawable.milk)
        )

        val gridLayout = findViewById<GridLayout>(R.id.gridLayout)

        for (product in products) {
            val itemView = layoutInflater.inflate(R.layout.product_item, null)

            val iv = itemView.findViewById<ImageView>(R.id.ivProduct)
            val name = itemView.findViewById<TextView>(R.id.tvName)
            val price = itemView.findViewById<TextView>(R.id.tvPrice)
            val btn = itemView.findViewById<Button>(R.id.btnAdd)

            iv.setImageResource(product.image)
            name.text = product.name
            price.text = "₹${product.price}"

            btn.setOnClickListener {
                val intent = Intent(this, CartActivity::class.java).apply {
                    action = Intent.ACTION_VIEW
                    putExtra("name", product.name)
                    putExtra("price", product.price)
                    putExtra("image", product.image)
                }
                startActivity(intent)
            }

            gridLayout.addView(itemView)
        }
    }
}
