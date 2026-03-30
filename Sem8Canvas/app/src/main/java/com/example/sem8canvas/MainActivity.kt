package com.example.sem8canvas

import android.animation.Animator
import android.animation.AnimatorInflater
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trafficlight.TrafficLightView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val trafficView = findViewById<TrafficLightView>(R.id.trafficView)
        val button = findViewById<Button>(R.id.btnDetach)

        button.setOnClickListener {
            trafficView.toggleDetach()
        }

        val imageView = findViewById<ImageView>(R.id.imageview)
//        val btnFade = findViewById<Button>(R.id.btnFade)
//        val btnMove = findViewById<Button>(R.id.btnMove)

//        btnFade.setOnClickListener {
//            val animator = AnimatorInflater.loadAnimator(this, R.animator.coinflip)
//            animator.setTarget(imageView)
//            animator.start()
//        }
//
//        btnMove.setOnClickListener {
//
//
//        }

    }



}