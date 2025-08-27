package com.example.unit2andriod

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inflate the custom toast layout
        val inflater: LayoutInflater = layoutInflater
        val view: View = inflater.inflate(R.layout.activity_main, null)

        // Customize the toast content
        val text: TextView = view.findViewById(R.id.toastText)
        text.text = "Hello, this is a custom Toast!"

        // Create and show the custom toast
        val toast = Toast(applicationContext)
        toast.duration = Toast.LENGTH_LONG
        toast.view = view
        toast.setGravity(Gravity.BOTTOM, 0, 100) // Optional: Adjust position
        toast.show()

    }
}
