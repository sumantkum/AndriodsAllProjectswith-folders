package com.example.sem8canvas

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var btnMove: Button
    private lateinit var mediaPlayer: MediaPlayer

    private var animatorSet: AnimatorSet? = null
    private val duration = 500L   // little smoother

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        imageView = findViewById(R.id.imageview)
        btnMove = findViewById(R.id.btnsound)

        mediaPlayer = MediaPlayer.create(this, R.raw.abc)

        btnMove.setOnClickListener {
            if (animatorSet == null || !animatorSet!!.isRunning) {
                mediaPlayer.start()
                moveInSquare()
            }
        }
    }

    private fun moveInSquare() {

        val parent = imageView.parent as ConstraintLayout

        parent.post {

            // Get actual start position
            val startX = imageView.x
            val startY = imageView.y

            // Calculate safe movement space
            val maxRight = parent.width - imageView.width - startX
            val maxDown = parent.height - imageView.height - startY

            val moveRight = ObjectAnimator.ofFloat(
                imageView,
                "translationX",
                0f,
                maxRight
            ).setDuration(duration)

            val moveDown = ObjectAnimator.ofFloat(
                imageView,
                "translationY",
                0f,
                maxDown
            ).setDuration(duration)

            val moveLeft = ObjectAnimator.ofFloat(
                imageView,
                "translationX",
                maxRight,
                0f
            ).setDuration(duration)

            val moveUp = ObjectAnimator.ofFloat(
                imageView,
                "translationY",
                maxDown,
                0f
            ).setDuration(duration)

            animatorSet = AnimatorSet()
            animatorSet?.playSequentially(
                moveRight,
                moveDown,
                moveLeft,
                moveUp
            )

            // Infinite loop
            animatorSet?.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    animatorSet?.start()
                }
            })

            animatorSet?.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        animatorSet?.cancel()
        mediaPlayer.release()
    }
}