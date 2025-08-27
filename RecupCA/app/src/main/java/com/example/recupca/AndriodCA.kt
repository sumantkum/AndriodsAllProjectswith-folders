package com.example.recupca

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AndriodCA : AppCompatActivity() {

    private val CHANNEL_SPORTS = "sports_news"
    private val CHANNEL_POLITICS = "politics_news"
    private val NOTIFICATION_PERMISSION_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_andriod_ca)

        // ✅ Request Notification permission if needed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_CODE
                )
            }
        }

        // ✅ Create notification channels
        createChannels()
    }

    private fun createChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(NotificationManager::class.java)
            val sportsChannel = NotificationChannel(
                CHANNEL_SPORTS, "Sports",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Sports news alerts"
            }

            val politicsChannel = NotificationChannel(
                CHANNEL_POLITICS, "Politics",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Politics news alerts"
            }

            manager?.createNotificationChannel(sportsChannel)
            manager?.createNotificationChannel(politicsChannel)
        }
    }

    fun sendSportsNotification(view: View) {
        if (hasNotificationPermission()) {
            notify(CHANNEL_SPORTS, "Sports Update", "India wins the match!", 1)
        } else {
            showPermissionToast()
        }
    }

    fun sendPoliticsNotification(view: View) {
        if (hasNotificationPermission()) {
            notify(CHANNEL_POLITICS, "Politics Update", "New law passed by government", 2)
        } else {
            showPermissionToast()
        }
    }

    private fun notify(channelId: String, title: String, text: String, id: Int) {
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        NotificationManagerCompat.from(this).notify(id, builder.build())
    }

    private fun hasNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun showPermissionToast() {
        Toast.makeText(this, "Please allow notification permission", Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NOTIFICATION_PERMISSION_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
