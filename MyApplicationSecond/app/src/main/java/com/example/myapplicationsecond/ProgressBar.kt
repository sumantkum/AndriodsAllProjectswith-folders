package com.example.myapplicationsecond

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class ProgressBarActivity : AppCompatActivity() {
    private var isProgressVisible = false
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_progress_bar)

        val loading = findViewById<ProgressBar>(R.id.progreebar) // Ensure correct ID
        val btn = findViewById<Button>(R.id.btn)

        loading.visibility = View.GONE

        btn.setOnClickListener {
            if (!isProgressVisible) {
                loading.visibility = View.VISIBLE
                isProgressVisible = true

                // Delay for 3 seconds (3000 ms) before switching activity
                handler.postDelayed({
                    loading.visibility = View.GONE
                    isProgressVisible = false
                    switchToNextActivity()
                }, 3000)
            }
        }
    }

    private fun switchToNextActivity() {
        val intent = Intent(this, MainActivity::class.java) // Change NextActivity to your actual activity name
        startActivity(intent)
        finish() // Close current activity
    }
}
