package com.example.sem8firebaseauth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class SpalceScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_spalce_screen)

        // ImageView from layout
        val splashImage = findViewById<ImageView>(R.id.splashLogo)

        // Glide image / gif load
        Glide.with(this)
            .load(R.drawable.logo) // your image or gif
            .into(splashImage)

        // Splash delay
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, RegisterUser::class.java))
            finish()
        }, 5000) // 3 seconds
    }
}
