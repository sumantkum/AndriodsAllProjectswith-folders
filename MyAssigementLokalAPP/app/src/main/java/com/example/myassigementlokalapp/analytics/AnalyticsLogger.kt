package com.example.passwordlessauth.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import timber.log.Timber

class AnalyticsLogger(context: Context) {

    private val firebaseAnalytics: FirebaseAnalytics =
        FirebaseAnalytics.getInstance(context)

    init {
        if (Timber.treeCount == 0) {
            Timber.plant(Timber.DebugTree())
        }
    }

    fun logOtpGenerated(email: String) {
        val bundle = Bundle().apply {
            putString("email", email)
            putLong("timestamp", System.currentTimeMillis())
        }
        firebaseAnalytics.logEvent("otp_generated", bundle)
        Timber.d("OTP Generated for: $email")
    }

    fun logOtpValidationSuccess(email: String) {
        val bundle = Bundle().apply {
            putString("email", email)
            putLong("timestamp", System.currentTimeMillis())
        }
        firebaseAnalytics.logEvent("otp_validation_success", bundle)
        Timber.i("OTP Validation Success for: $email")
    }

    fun logOtpValidationFailure(
        email: String,
        reason: String,
        attemptsRemaining: Int? = null
    ) {
        val bundle = Bundle().apply {
            putString("email", email)
            putString("failure_reason", reason)
            attemptsRemaining?.let { putInt("attempts_remaining", it) }
            putLong("timestamp", System.currentTimeMillis())
        }
        firebaseAnalytics.logEvent("otp_validation_failure", bundle)
        Timber.w("OTP Validation Failed for: $email, Reason: $reason")
    }

    fun logLogout(email: String, sessionDurationSeconds: Long) {
        val bundle = Bundle().apply {
            putString("email", email)
            putLong("session_duration_seconds", sessionDurationSeconds)
            putLong("timestamp", System.currentTimeMillis())
        }
        firebaseAnalytics.logEvent("user_logout", bundle)
        Timber.i("User Logout: $email, Duration: ${sessionDurationSeconds}s")
    }
}
