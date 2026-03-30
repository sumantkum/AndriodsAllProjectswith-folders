package com.example.sem8canvas

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val imageView = findViewById<ImageView>(R.id.imageview)
        val btnFlip = findViewById<Button>(R.id.btnFlip)
        val btnTranslate = findViewById<Button>(R.id.btnTranslate)
        val btnRotate = findViewById<Button>(R.id.btnRotate)
        val btnFade = findViewById<Button>(R.id.btnFade)

        // 🔄 Flip
        btnFlip.setOnClickListener {
            imageView.animate()
                .rotationYBy(180f)
                .setDuration(1000)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .start()
        }

        // ➡ Translate Left ↔ Right
        btnTranslate.setOnClickListener {
            val animator = ObjectAnimator.ofFloat(imageView, "translationX", 0f, 500f)
            animator.duration = 1000
            animator.repeatCount = ValueAnimator.INFINITE
            animator.repeatMode = ValueAnimator.REVERSE
            animator.start()
        }

        // 🔁 Rotate
        btnRotate.setOnClickListener {
            imageView.animate()
                .rotationBy(360f)
                .setDuration(1000)
                .start()
        }

        // 🌫 Fade
        btnFade.setOnClickListener {
            imageView.animate()
                .alpha(0f)
                .setDuration(1000)
                .withEndAction {
                    imageView.animate()
                        .alpha(1f)
                        .setDuration(1000)
                        .start()
                }
                .start()
        }
    }
}
