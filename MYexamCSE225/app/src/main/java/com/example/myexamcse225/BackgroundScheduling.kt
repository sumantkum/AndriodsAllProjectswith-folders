package com.example.myexamcse225

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BackgroundScheduling : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_background_scheduling)

        // Intent to Second Activity
        val btnNext: Button = findViewById(R.id.btnNext)
        btnNext.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("username", "Sumant")
            startActivity(intent)

        }

        // AlarmManager Example
        val btnAlarm: Button = findViewById(R.id.btnAlarm)
        btnAlarm.setOnClickListener {
            val intent = Intent(this, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+60000, pendingIntent)
        }


    }
}