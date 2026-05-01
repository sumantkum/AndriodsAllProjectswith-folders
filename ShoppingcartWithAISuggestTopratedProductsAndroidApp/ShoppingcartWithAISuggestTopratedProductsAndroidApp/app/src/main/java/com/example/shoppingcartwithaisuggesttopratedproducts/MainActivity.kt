package com.example.shoppingcartwithaisuggesttopratedproducts

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ProductAdapter
    private val gson = Gson()

    // UI elements
    private lateinit var rvProducts: RecyclerView
    private lateinit var btnFetch: Button
    private lateinit var btnCart: Button
    private lateinit var etCategory: EditText
    private lateinit var tvStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // findViewById replacements
        rvProducts = findViewById(R.id.rvProducts)
        btnFetch = findViewById(R.id.btnFetch)
        btnCart = findViewById(R.id.btnCart)
        etCategory = findViewById(R.id.etCategory)
        tvStatus = findViewById(R.id.tvStatus)

        adapter = ProductAdapter(mutableListOf()) { updateCartCount() }
        rvProducts.layoutManager = LinearLayoutManager(this)
        rvProducts.adapter = adapter

        btnFetch.setOnClickListener { fetchSuggestions() }
        btnCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        updateCartCount()
    }

    private fun updateCartCount() {
        btnCart.text = "Cart (${CartManager.count()})"
    }

    private fun fetchSuggestions() {
        val category = etCategory.text.toString().ifBlank { "gadgets" }
        tvStatus.text = "Fetching top-rated $category..."
        tvStatus.visibility = View.VISIBLE

        val prompt = """
You are a shopping assistant.
Return ONLY a **valid JSON array** of 6 top-rated $category in India.

Rules:
- No markdown, no commentary, no explanation.
- Do not wrap JSON in ```json ``` fences.
- The output must start with `[` and end with `]`.

Each product object must be like:
{
  "id": "<short-id>",
  "name": "<product name>",
  "description": "<1-2 line description>",
  "rating": <decimal between 4.0 and 5.0>,
  "price": <number in INR>
}
""".trimIndent()

        val req = GeminiRequest(
            contents = listOf(GeminiContent(parts = listOf(GeminiPart(prompt))))
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val resp = RetrofitClient.api.generateContent(req).awaitResponse()
                val text = resp.body()
                    ?.candidates?.firstOrNull()
                    ?.content?.parts?.firstOrNull()?.text

                val products: List<Product> = parseProducts(text)
                withContext(Dispatchers.Main) {
                    if (products.isEmpty()) {
                        tvStatus.text = "Could not parse results. Try again."
                    } else {
                        tvStatus.text = "Top rated ${category.lowercase()}:"
                        adapter.submit(products)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    tvStatus.text = "Error: ${e.message}"
                }
            }
        }
    }

    private fun parseProducts(text: String?): List<Product> {
        if (text.isNullOrBlank()) return emptyList()
        return try {
            var cleaned = text
                .replace("```json", "")
                .replace("```", "")
                .trim()

            // Ensure response starts/ends properly
            val start = cleaned.indexOf("[")
            val end = cleaned.lastIndexOf("]")
            if (start != -1 && end != -1 && end > start) {
                cleaned = cleaned.substring(start, end + 1)
            }

            val type = object : TypeToken<List<Product>>() {}.type
            gson.fromJson<List<Product>>(cleaned, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
