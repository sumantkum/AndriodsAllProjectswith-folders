package com.example.sem7testing

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val username = findViewById<EditText>(R.id.edtUsername)
        val btn = findViewById<Button>(R.id.btnSubmit)
        val welcom = findViewById<TextView>(R.id.txtWelcome)

        btn.setOnClickListener{
            welcom.text = "Welcome ${username.text}"
        }

    }
}