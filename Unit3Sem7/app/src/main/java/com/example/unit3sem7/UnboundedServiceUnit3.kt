package com.example.unit3sem7

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UnboundedServiceUnit3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_unbounded_service_unit3)

        val btnStartService = findViewById<Button>(R.id.button_start_download)
        val btnStopService = findViewById<Button>(R.id.button_stop_download)

        btnStartService.setOnClickListener {
            Intent(this, DownloadServiceK002::class.java).also {
                startService(it)
            }
        }
        btnStopService.setOnClickListener {
            Intent(this, DownloadServiceK002::class.java).also {
                stopService(it)
            }
        }


    }
}