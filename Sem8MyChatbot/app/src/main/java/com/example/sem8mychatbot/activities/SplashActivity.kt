package com.gemini.aichatbot.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gemini.aichatbot.R
import com.gemini.aichatbot.databinding.ActivitySplashBinding
import com.gemini.aichatbot.utils.PreferencesManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Splash screen shown at app launch.
 * Displays animated logo and transitions to onboarding or main chat.
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var prefs: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = PreferencesManager(this)

        setupAnimations()
        navigateAfterDelay()
    }

    private fun setupAnimations() {
        // Play Lottie logo animation
        binding.lottieLogoAnim.apply {
            setAnimation(R.raw.ai_brain_animation)
            playAnimation()
        }

        // Fade in app name with delay
        binding.tvAppName.alpha = 0f
        binding.tvAppName.animate()
            .alpha(1f)
            .setStartDelay(800)
            .setDuration(600)
            .start()

        // Slide up tagline
        binding.tvTagline.translationY = 50f
        binding.tvTagline.alpha = 0f
        binding.tvTagline.animate()
            .translationY(0f)
            .alpha(1f)
            .setStartDelay(1200)
            .setDuration(600)
            .start()

        // Animated gradient background via Lottie
        binding.lottieBgAnim.apply {
            setAnimation(R.raw.gradient_bg_animation)
            playAnimation()
        }
    }

    private fun navigateAfterDelay() {
        lifecycleScope.launch {
            delay(2800) // Show splash for ~3 seconds

            val intent = if (prefs.isOnboardingDone) {
                Intent(this@SplashActivity, MainActivity::class.java)
            } else {
                Intent(this@SplashActivity, OnboardingActivity::class.java)
            }

            startActivity(intent)

            // Smooth fade-out transition
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}