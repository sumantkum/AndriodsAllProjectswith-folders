package com.example.unit2andriod

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CompareIntent1 : AppCompatActivity() {
    private val CHANNEL_ID = "channel_id_example"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_compare_intent1)


        createNotificationChannel()

        val btnOpen = findViewById<Button>(R.id.btnOpen)
        val btnNotify = findViewById<Button>(R.id.btnNotify)

        // Direct Intent
        btnOpen.setOnClickListener {
            val intent = Intent(this, CompareIntent2::class.java)
            startActivity(intent)
        }

        // PendingIntent via Notification
        btnNotify.setOnClickListener {
            val intent = Intent(this, CompareIntent2::class.java)

            val pendingIntent = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Tap to open")
                .setContentText("This will open SecondActivity")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            val notificationManager = NotificationManagerCompat.from(this)
            notificationManager.notify(2, builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Demo Channel"
            val description = "Used for showing demo notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                this.description = description
            }

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

    }
}