package com.example.passwordlessauth.data

import kotlin.random.Random

/**
 * Manages OTP generation and validation for multiple email addresses.
 * Uses a Map to store OTP data per email, ensuring isolation between users.
 */
class OtpManager {
    // Map to store OTP data per email address
    // Key: email, Value: OtpData
    private val otpStorage = mutableMapOf<String, OtpData>()

    /**
     * Generates a new 6-digit OTP for the given email.
     * - Invalidates any existing OTP for this email
     * - Resets attempt count to 3
     *
     * @param email The email address to generate OTP for
     * @return The generated 6-digit OTP code
     */
    fun generateOtp(email: String): String {
        // Generate random 6-digit code (100000 to 999999)
        val code = Random.nextInt(100000, 1000000).toString()

        // Store with current timestamp and reset attempts
        otpStorage[email] = OtpData(
            code = code,
            generatedAt = System.currentTimeMillis(),
            attemptsRemaining = 3
        )

        return code
    }

    /**
     * Validates the entered OTP for a given email.
     *
     * @param email The email address
     * @param enteredOtp The OTP entered by user
     * @return ValidationResult indicating success or specific failure reason
     */
    fun validateOtp(email: String, enteredOtp: String): ValidationResult {
        val otpData = otpStorage[email]
            ?: return ValidationResult.NoOtpGenerated

        // Check if expired
        if (otpData.isExpired()) {
            return ValidationResult.Expired
        }

        // Check if no attempts remaining
        if (otpData.attemptsRemaining <= 0) {
            return ValidationResult.AttemptsExceeded
        }

        // Check if OTP matches
        return if (otpData.code == enteredOtp) {
            // Success - remove OTP from storage
            otpStorage.remove(email)
            ValidationResult.Success
        } else {
            // Incorrect - decrement attempts
            val updatedData = otpData.copy(
                attemptsRemaining = otpData.attemptsRemaining - 1
            )
            otpStorage[email] = updatedData
            ValidationResult.Incorrect(updatedData.attemptsRemaining)
        }
    }

    /**
     * Gets the OTP data for a specific email (for UI display of timer/attempts)
     */
    fun getOtpData(email: String): OtpData? {
        return otpStorage[email]
    }

    /**
     * Clears OTP data for a specific email
     */
    fun clearOtp(email: String) {
        otpStorage.remove(email)
    }

    /**
     * Sealed class representing all possible OTP validation outcomes
     */
    sealed class ValidationResult {
        object Success : ValidationResult()
        object NoOtpGenerated : ValidationResult()
        object Expired : ValidationResult()
        object AttemptsExceeded : ValidationResult()
        data class Incorrect(val attemptsRemaining: Int) : ValidationResult()
    }
}