package com.gemini.aichatbot.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.gemini.aichatbot.R
import com.gemini.aichatbot.databinding.ActivitySettingsBinding
import com.gemini.aichatbot.utils.PreferencesManager

/**
 * Settings screen — configure API key, dark mode, voice, and other preferences.
 */
class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var prefs: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = PreferencesManager(this)

        loadSettings()
        setupListeners()
        setupToolbar()
    }

    private fun setupToolbar() {
        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun loadSettings() {
        binding.apply {
            // Load saved values into UI
            etApiKey.setText(prefs.customApiKey)
            etUserName.setText(prefs.userName)
            switchDarkMode.isChecked = prefs.isDarkMode
            switchVoiceInput.isChecked = prefs.isVoiceInputEnabled
            switchTypingAnimation.isChecked = prefs.isTypingAnimationEnabled

            // AI personality spinner
            val personalities = arrayOf("Helpful", "Creative", "Concise", "Friendly", "Technical")
            val currentIdx = personalities.indexOfFirst {
                it.lowercase() == prefs.aiPersonality
            }.coerceAtLeast(0)
        }
    }

    private fun setupListeners() {
        binding.apply {
            // Dark mode toggle
            switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
                prefs.isDarkMode = isChecked
                AppCompatDelegate.setDefaultNightMode(
                    if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
                    else AppCompatDelegate.MODE_NIGHT_NO
                )
            }

            // Voice input toggle
            switchVoiceInput.setOnCheckedChangeListener { _, isChecked ->
                prefs.isVoiceInputEnabled = isChecked
            }

            // Typing animation toggle
            switchTypingAnimation.setOnCheckedChangeListener { _, isChecked ->
                prefs.isTypingAnimationEnabled = isChecked
            }

            // Save API key
            btnSaveApiKey.setOnClickListener {
                val key = etApiKey.text.toString().trim()
                if (key.isEmpty()) {
                    Toast.makeText(this@SettingsActivity, "Please enter a valid API key", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                prefs.customApiKey = key
                Toast.makeText(this@SettingsActivity, "API key saved!", Toast.LENGTH_SHORT).show()
            }

            // Save user name
            btnSaveName.setOnClickListener {
                val name = etUserName.text.toString().trim()
                if (name.isNotEmpty()) {
                    prefs.userName = name
                    Toast.makeText(this@SettingsActivity, "Name saved!", Toast.LENGTH_SHORT).show()
                }
            }

            // App version
            tvVersion.text = "Version 1.0.0 · Gemini AI Smart Chatbot"
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}