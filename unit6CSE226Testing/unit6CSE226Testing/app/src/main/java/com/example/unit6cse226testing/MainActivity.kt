package com.example.unit6cse226testing

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.unit5cse226currentlocationtopic.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val num1 = findViewById<EditText>(R.id.num1)
        val num2 = findViewById<EditText>(R.id.num2)
        val result = findViewById<TextView>(R.id.result)
        val btnAdd = findViewById<Button>(R.id.btnAdd)

        btnAdd.setOnClickListener {
            val sum = addNumbers(
                num1.text.toString().toInt(),
                num2.text.toString().toInt()
            )
            result.text = "Result: $sum"
            Log.d("Checking value", " sum")
        }
    }

    // Function we’ll test
    fun addNumbers(a: Int, b: Int): Int {
        Log.d("MainActivity", "addNumbers() called with $a and $b")
        return a + b
    }
}

