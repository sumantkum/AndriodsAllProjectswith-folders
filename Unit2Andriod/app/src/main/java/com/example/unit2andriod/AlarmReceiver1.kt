package com.example.unit2andriod

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AlarmReceiver1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_receiver1)

        val btnSetAlarm = findViewById<Button>(R.id.btnSetAlarm)

        btnSetAlarm.setOnClickListener {
            val intent = Intent(this, AlarmSet::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )

            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val triggerTime = System.currentTimeMillis() + 5000 // after 5 seconds

            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
            )

            Toast.makeText(this, "Alarm set for 5 seconds later", Toast.LENGTH_SHORT).show()
        }
    }
}
