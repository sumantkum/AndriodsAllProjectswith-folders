package com.example.newsapp

import android.app.*
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.view.View
import com.example.recupca.R

class MainActivity : AppCompatActivity() {

    private val CHANNEL_SPORTS = "sports_news"
    private val CHANNEL_POLITICS = "politics_news"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createChannels()
    }

    private fun createChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(
                NotificationChannel(CHANNEL_SPORTS, "Sports", NotificationManager.IMPORTANCE_HIGH).apply {
                    description = "Sports news alerts"
                })
            manager?.createNotificationChannel(
                NotificationChannel(CHANNEL_POLITICS, "Politics", NotificationManager.IMPORTANCE_DEFAULT).apply {
                    description = "Politics news alerts"
                })
        }
    }

    fun sendSports(view: View) {
        notify(CHANNEL_SPORTS, "Sports Update", "India wins the match!", 1)
    }

    fun sendPolitics(view: View) {
        notify(CHANNEL_POLITICS, "Politics Update", "New law passed by government", 2)
    }

    private fun notify(channelId: String, title: String, text: String, id: Int) {
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_news)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        NotificationManagerCompat.from(this).notify(id, builder.build())
    }
}
