package com.example.unit3sem7

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder

class MusicServiceKO002 : Service() {

    private val binder = LocalBinder()
    private var mediaPlayer : MediaPlayer? =null

    inner class LocalBinder : Binder(){
        fun getService() : MusicServiceKO002 = this@MusicServiceKO002
    }

    override fun onBind(intent: Intent?): IBinder? {
        if(mediaPlayer == null) {
//            mediaPlayer = MediaPlayer.create(this, R.raw.sample( ))
            mediaPlayer?. isLooping = true
        }
        return binder
    }

}