package com.example.shoppingcartwithaisuggesttopratedproducts

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {

    private lateinit var adapter: CartAdapter

    // UI elements
    private lateinit var rvCart: RecyclerView
    private lateinit var btnCheckout: Button
    private lateinit var tvTotal: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        // findViewById replacements
        rvCart = findViewById(R.id.rvCart)
        btnCheckout = findViewById(R.id.btnCheckout)
        tvTotal = findViewById(R.id.tvTotal)

        adapter = CartAdapter(CartManager.all().toMutableList()) { updateTotal() }
        rvCart.layoutManager = LinearLayoutManager(this)
        rvCart.adapter = adapter

        btnCheckout.setOnClickListener {
            btnCheckout.isEnabled = false
            btnCheckout.text = "Order Placed!"
        }

        updateTotal()
    }

    private fun updateTotal() {
        tvTotal.text = "Total: ₹${"%.2f".format(CartManager.total())}"
    }
}
