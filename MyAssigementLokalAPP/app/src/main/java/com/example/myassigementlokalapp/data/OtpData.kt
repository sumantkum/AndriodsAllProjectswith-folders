package com.example.passwordlessauth.data

/**
 * Represents the OTP state for a specific email address.
 *
 * @property code The 6-digit OTP code
 * @property generatedAt Timestamp when OTP was generated (System.currentTimeMillis())
 * @property attemptsRemaining Number of attempts left (starts at 3)
 * @property expiryDurationMs Duration in milliseconds before OTP expires (60 seconds)
 */
data class OtpData(
    val code: String,
    val generatedAt: Long,
    val attemptsRemaining: Int = 3,
    val expiryDurationMs: Long = 60_000L // 60 seconds
) {
    /**
     * Checks if the OTP has expired based on current time
     */
    fun isExpired(): Boolean {
        return System.currentTimeMillis() - generatedAt > expiryDurationMs
    }

    /**
     * Gets remaining time in milliseconds
     */
    fun getRemainingTimeMs(): Long {
        val elapsed = System.currentTimeMillis() - generatedAt
        return (expiryDurationMs - elapsed).coerceAtLeast(0)
    }
}