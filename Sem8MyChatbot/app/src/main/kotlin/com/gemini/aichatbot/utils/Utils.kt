package com.gemini.aichatbot.utils

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.view.inputmethod.InputMethodManager

// ─────────────────────────────────────────────────────────
// Preferences Manager
// ─────────────────────────────────────────────────────────

/**
 * Manages all SharedPreferences for the app.
 */
class PreferencesManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("gemini_prefs", Context.MODE_PRIVATE)

    companion object {
        const val KEY_ONBOARDING_DONE = "onboarding_done"
        const val KEY_DARK_MODE = "dark_mode"
        const val KEY_API_KEY = "gemini_api_key"
        const val KEY_VOICE_INPUT = "voice_input_enabled"
        const val KEY_TYPING_ANIMATION = "typing_animation"
        const val KEY_USER_NAME = "user_name"
        const val KEY_AI_PERSONALITY = "ai_personality"
    }

    var isOnboardingDone: Boolean
        get() = prefs.getBoolean(KEY_ONBOARDING_DONE, false)
        set(value) = prefs.edit().putBoolean(KEY_ONBOARDING_DONE, value).apply()

    var isDarkMode: Boolean
        get() = prefs.getBoolean(KEY_DARK_MODE, true)
        set(value) = prefs.edit().putBoolean(KEY_DARK_MODE, value).apply()

    var customApiKey: String
        get() = prefs.getString(KEY_API_KEY, "") ?: ""
        set(value) = prefs.edit().putString(KEY_API_KEY, value).apply()

    var isVoiceInputEnabled: Boolean
        get() = prefs.getBoolean(KEY_VOICE_INPUT, true)
        set(value) = prefs.edit().putBoolean(KEY_VOICE_INPUT, value).apply()

    var isTypingAnimationEnabled: Boolean
        get() = prefs.getBoolean(KEY_TYPING_ANIMATION, true)
        set(value) = prefs.edit().putBoolean(KEY_TYPING_ANIMATION, value).apply()

    var userName: String
        get() = prefs.getString(KEY_USER_NAME, "You") ?: "You"
        set(value) = prefs.edit().putString(KEY_USER_NAME, value).apply()

    var aiPersonality: String
        get() = prefs.getString(KEY_AI_PERSONALITY, "helpful") ?: "helpful"
        set(value) = prefs.edit().putString(KEY_AI_PERSONALITY, value).apply()
}

// ─────────────────────────────────────────────────────────
// Extension Functions
// ─────────────────────────────────────────────────────────

/**
 * Handy Kotlin extension functions used throughout the app
 */

// Show/Hide keyboard
fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

// Visibility helpers
fun View.visible() { visibility = View.VISIBLE }
fun View.gone() { visibility = View.GONE }
fun View.invisible() { visibility = View.INVISIBLE }

// Vibrate feedback
fun Context.vibrateShort() {
    val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as android.os.Vibrator
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        vibrator.vibrate(
            android.os.VibrationEffect.createOneShot(
                50, android.os.VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    }
}