package com.example.sem8uiux

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val loginBtn = findViewById<Button>(R.id.btnLogin)
        val createAccount = findViewById<TextView>(R.id.createAccount)

        createAccount.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
            Toast.makeText(this, "Redirecting to Create Account Page", Toast.LENGTH_SHORT).show()
        }

        loginBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity3::class.java))
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
        }

    }
}