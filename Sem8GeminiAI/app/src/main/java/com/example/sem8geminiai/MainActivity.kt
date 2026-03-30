package com.example.sem8geminiai

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    lateinit var etFood: EditText
    lateinit var btnSuggest: Button
    lateinit var txtResult: TextView
    lateinit var loader: ProgressBar

    val apiKey = "AIzaSyDs-34GDItgMGhAO3XkuNd8TuWisfjLncg"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etFood = findViewById(R.id.etFood)
        btnSuggest = findViewById(R.id.btnSuggest)
        txtResult = findViewById(R.id.txtResult)
        loader = findViewById(R.id.loader)

        btnSuggest.setOnClickListener {

            val userInput = etFood.text.toString()

            if(userInput.isEmpty()){
                Toast.makeText(this,"Please enter preference",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            getFoodSuggestion(userInput)
        }
    }

    private fun getFoodSuggestion(query: String) {

        val generativeModel = GenerativeModel(
            modelName = "gemini-2.5-flash",
            apiKey = apiKey
        )

        loader.visibility = View.VISIBLE
        txtResult.text = ""

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val prompt = "Suggest 5 food items for $query lovers"

                val response = generativeModel.generateContent(prompt)

                withContext(Dispatchers.Main) {

                    loader.visibility = View.GONE
                    txtResult.text = response.text
                }

            } catch (e: Exception) {

                withContext(Dispatchers.Main) {

                    loader.visibility = View.GONE
                    txtResult.text = "Error: ${e.message}"
                }
            }
        }
    }
}