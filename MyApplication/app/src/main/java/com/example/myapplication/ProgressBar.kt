package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class ProgressBarActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView
    private lateinit var startButton: Button
    private var progressStatus = 0
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main) // Ensure your XML file exists

        // Initialize UI elements
        progressBar = findViewById(R.id.progressBar)
        progressText = findViewById(R.id.progressText)
        startButton = findViewById(R.id.startButton)

        // Start progress when button is clicked
        startButton.setOnClickListener {
            startProgress()
        }
    }

    private fun startProgress() {
        progressStatus = 0
        Thread {
            while (progressStatus < 100) {
                progressStatus += 1
                handler.post {
                    progressBar.progress = progressStatus
                    progressText.text = "Progress: $progressStatus%"
                }
                Thread.sleep(100) // Smooth update delay
            }
        }.start()
    }
}
