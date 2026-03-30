package com.example.unit3sem7

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.*

class DownloadServiceK002 : Service() {

    private val serviceScope = CoroutineScope(Dispatchers.IO + Job())

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Download Service started", Toast.LENGTH_LONG).show()
        Log.d("Download Service", "Service Started")
        serviceScope.launch {
            for (i in 1..5) {
                delay(2000)
                Log.d("Download Service", "Downloading file...$i/5")
            }
            Log.d("Download Service", "Download Completed")
            stopSelf()
        }
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Download service stop", Toast.LENGTH_LONG).show()
        serviceScope.cancel()
        Log.d("Dpwnload Service", "Service destoyed")
    }
}