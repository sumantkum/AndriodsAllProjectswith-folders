package com.example.sem8snsor // Matches your Manifest package

import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// CLASS NAME UPDATED: Matches <activity android:name=".DuckActivity" />
class DuckActivity : AppCompatActivity() {
    private lateinit var duckImageView: ImageView
    private lateinit var btnJump: Button
    private var isAnimating = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Ensure this XML filename matches your layout file!
        setContentView(R.layout.activity_duck)

        val mainView = findViewById<View>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        duckImageView = findViewById(R.id.duckImageView)
        btnJump = findViewById(R.id.btnJump)

        btnJump.setOnClickListener {
            if (!isAnimating) {
                duckImageView.post { prepareJump() }
            }
        }
    }

    private fun prepareJump() {
        isAnimating = true
        btnJump.isEnabled = false

        // Reset positions
        duckImageView.translationX = 0f
        duckImageView.translationY = 0f
        duckImageView.scaleX = 1f
        duckImageView.scaleY = 1f

        val rootLayout = findViewById<View>(R.id.main)
        val margin = if (duckImageView.left > 0) duckImageView.left.toFloat() else 50f
        val totalDistance = rootLayout.width.toFloat() - duckImageView.width.toFloat() - (margin * 2)

        if (totalDistance > 0) {
            executeJumpCycle(0, 3, totalDistance / 3)
        } else {
            isAnimating = false
            btnJump.isEnabled = true
        }
    }

    private fun executeJumpCycle(currentStep: Int, totalSteps: Int, stepDistance: Float) {
        if (currentStep >= totalSteps) {
            isAnimating = false
            btnJump.isEnabled = true
            return
        }

        // Squash
        duckImageView.animate()
            .scaleY(0.7f).scaleX(1.3f)
            .setDuration(150)
            .withEndAction {
                // Launch
                duckImageView.animate()
                    .translationY(-400f)
                    .translationX(duckImageView.translationX + (stepDistance / 2))
                    .scaleY(1.3f).scaleX(0.7f)
                    .setInterpolator(DecelerateInterpolator())
                    .setDuration(350)
                    .withEndAction {
                        // Fall
                        duckImageView.animate()
                            .translationY(0f)
                            .translationX(duckImageView.translationX + (stepDistance / 2))
                            .scaleY(1.0f).scaleX(1.0f)
                            .setInterpolator(AccelerateDecelerateInterpolator())
                            .setDuration(350)
                            .withEndAction {
                                duckLandingBounce(currentStep, totalSteps, stepDistance)
                            }
                    }
            }
    }

    private fun duckLandingBounce(currentStep: Int, totalSteps: Int, stepDistance: Float) {
        duckImageView.animate()
            .scaleY(0.85f).scaleX(1.15f)
            .setDuration(100)
            .withEndAction {
                duckImageView.animate()
                    .scaleY(1.0f).scaleX(1.0f)
                    .setDuration(100)
                    .withEndAction {
                        executeJumpCycle(currentStep + 1, totalSteps, stepDistance)
                    }
            }
    }
}