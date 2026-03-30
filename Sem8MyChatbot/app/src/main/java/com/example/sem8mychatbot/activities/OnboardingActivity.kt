package com.gemini.aichatbot.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.gemini.aichatbot.R
import com.gemini.aichatbot.adapters.OnboardingAdapter
import com.gemini.aichatbot.databinding.ActivityOnboardingBinding
import com.gemini.aichatbot.models.OnboardingPage
import com.gemini.aichatbot.utils.PreferencesManager

/**
 * Onboarding screen with 3 tutorial pages shown on first app launch.
 * Uses ViewPager2 with a dots indicator and smooth page transitions.
 */
class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var prefs: PreferencesManager

    private val pages = listOf(
        OnboardingPage(
            title = "Meet Your AI Assistant",
            description = "Powered by Google Gemini — the most advanced AI available. Ask anything, get instant intelligent answers.",
            lottieAnimation = "ai_hello_animation.json",
            gradientStart = R.color.gradient_start_1,
            gradientEnd = R.color.gradient_end_1
        ),
        OnboardingPage(
            title = "Smart Conversations",
            description = "Hold multi-turn conversations with full context. Gemini remembers what you've discussed and builds on it.",
            lottieAnimation = "chat_animation.json",
            gradientStart = R.color.gradient_start_2,
            gradientEnd = R.color.gradient_end_2
        ),
        OnboardingPage(
            title = "Voice & Beyond",
            description = "Use voice input, react to messages, share responses, and revisit all your conversation history anytime.",
            lottieAnimation = "voice_animation.json",
            gradientStart = R.color.gradient_start_3,
            gradientEnd = R.color.gradient_end_3
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = PreferencesManager(this)

        setupViewPager()
        setupButtons()
    }

    private fun setupViewPager() {
        val adapter = OnboardingAdapter(pages)
        binding.viewPager.adapter = adapter

        // Connect dots indicator to ViewPager2
        binding.dotsIndicator.attachTo(binding.viewPager)

        // Update buttons on page change
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateButtons(position)
                animatePageTransition(position)
            }
        })
    }

    private fun setupButtons() {
        binding.btnNext.setOnClickListener {
            val current = binding.viewPager.currentItem
            if (current < pages.size - 1) {
                binding.viewPager.currentItem = current + 1
            } else {
                finishOnboarding()
            }
        }

        binding.btnSkip.setOnClickListener {
            finishOnboarding()
        }
    }

    private fun updateButtons(position: Int) {
        if (position == pages.size - 1) {
            // Last page: show "Get Started" and hide skip
            binding.btnNext.text = "Get Started"
            binding.btnSkip.visibility = View.INVISIBLE
        } else {
            binding.btnNext.text = "Next"
            binding.btnSkip.visibility = View.VISIBLE
        }
    }

    private fun animatePageTransition(position: Int) {
        // Animate the background gradient change
        binding.root.animate()
            .alpha(0.7f)
            .setDuration(150)
            .withEndAction {
                binding.root.animate()
                    .alpha(1f)
                    .setDuration(150)
                    .start()
            }.start()
    }

    private fun finishOnboarding() {
        prefs.isOnboardingDone = true
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }
}