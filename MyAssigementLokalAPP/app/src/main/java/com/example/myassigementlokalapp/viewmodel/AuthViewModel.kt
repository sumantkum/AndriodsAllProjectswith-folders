package com.example.passwordlessauth.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordlessauth.analytics.AnalyticsLogger
import com.example.passwordlessauth.data.OtpManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for managing authentication state and business logic.
 *
 * Architecture Pattern:
 * - One-way data flow: UI emits events -> ViewModel processes -> UI observes state
 * - UI State is exposed as StateFlow (immutable from UI perspective)
 * - Business logic is kept separate from UI logic
 *
 * Why AndroidViewModel?
 * - Need Application context for AnalyticsLogger initialization
 * - Survives configuration changes (screen rotation)
 */

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    // Business logic components
    private val otpManager = OtpManager()
    private val analyticsLogger = AnalyticsLogger(application)

    // Mutable state for internal updates
    private val _authState = MutableStateFlow<AuthState>(AuthState.EmailEntry)

    // Public immutable state for UI observation
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    /**
     * Generates and sends OTP for the given email.
     * Called when user taps "Send OTP" button.
     */
    fun sendOtp(email: String) {
        viewModelScope.launch {
            // Generate OTP (this invalidates any previous OTP for this email)
            val otp = otpManager.generateOtp(email)

            // Log analytics event
            analyticsLogger.logOtpGenerated(email)

            // In a real app, this OTP would be sent via email/SMS
            // For this demo, we'll just log it
            println("Generated OTP for $email: $otp")

            // Update UI state to show OTP entry screen
            _authState.value = AuthState.OtpEntry(email)
        }
    }

    /**
     * Validates the entered OTP.
     * Called when user submits OTP.
     */
    fun validateOtp(email: String, enteredOtp: String) {
        viewModelScope.launch {
            when (val result = otpManager.validateOtp(email, enteredOtp)) {
                is OtpManager.ValidationResult.Success -> {
                    // Log success
                    analyticsLogger.logOtpValidationSuccess(email)

                    // Update state to authenticated
                    _authState.value = AuthState.Authenticated(
                        email = email,
                        sessionStartTime = System.currentTimeMillis()
                    )
                }

                is OtpManager.ValidationResult.Incorrect -> {
                    // Log failure
                    analyticsLogger.logOtpValidationFailure(
                        email = email,
                        reason = "incorrect_code",
                        attemptsRemaining = result.attemptsRemaining
                    )

                    // Update state with error message
                    _authState.value = AuthState.OtpEntry(
                        email = email,
                        errorMessage = "Incorrect OTP. ${result.attemptsRemaining} attempts remaining."
                    )
                }

                is OtpManager.ValidationResult.Expired -> {
                    // Log failure
                    analyticsLogger.logOtpValidationFailure(
                        email = email,
                        reason = "expired"
                    )

                    // Update state with error message
                    _authState.value = AuthState.OtpEntry(
                        email = email,
                        errorMessage = "OTP has expired. Please request a new one."
                    )
                }

                is OtpManager.ValidationResult.AttemptsExceeded -> {
                    // Log failure
                    analyticsLogger.logOtpValidationFailure(
                        email = email,
                        reason = "attempts_exceeded"
                    )

                    // Update state with error message
                    _authState.value = AuthState.OtpEntry(
                        email = email,
                        errorMessage = "Maximum attempts exceeded. Please request a new OTP."
                    )
                }

                is OtpManager.ValidationResult.NoOtpGenerated -> {
                    // Log failure
                    analyticsLogger.logOtpValidationFailure(
                        email = email,
                        reason = "no_otp_generated"
                    )

                    // Shouldn't happen in normal flow, but handle gracefully
                    _authState.value = AuthState.EmailEntry
                }
            }
        }
    }

    /**
     * Logs out the current user.
     * Called when user taps "Logout" button.
     */
    fun logout() {
        viewModelScope.launch {
            val currentState = _authState.value
            if (currentState is AuthState.Authenticated) {
                // Calculate session duration
                val sessionDurationMs = System.currentTimeMillis() - currentState.sessionStartTime
                val sessionDurationSeconds = sessionDurationMs / 1000

                // Log logout event
                analyticsLogger.logLogout(currentState.email, sessionDurationSeconds)

                // Clear any remaining OTP data
                otpManager.clearOtp(currentState.email)

                // Return to email entry state
                _authState.value = AuthState.EmailEntry
            }
        }
    }

    /**
     * Allows user to go back to email entry from OTP screen
     */
    fun backToEmailEntry() {
        viewModelScope.launch {
            _authState.value = AuthState.EmailEntry
        }
    }

    /**
     * Gets current OTP data for display (countdown timer, attempts)
     */
    fun getOtpData(email: String) = otpManager.getOtpData(email)
}