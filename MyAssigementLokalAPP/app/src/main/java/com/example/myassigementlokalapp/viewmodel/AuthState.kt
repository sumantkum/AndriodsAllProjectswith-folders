package com.example.passwordlessauth.viewmodel

/**
 * Sealed class representing the authentication state of the app.
 * This enables type-safe state management and makes it clear what data
 * is available in each state.
 */
sealed class AuthState {
    /**
     * Initial state - user needs to enter email
     */
    object EmailEntry : AuthState()

    /**
     * OTP has been sent, waiting for user to enter it
     * @param email The email OTP was sent to
     * @param errorMessage Optional error message to display
     */
    data class OtpEntry(
        val email: String,
        val errorMessage: String? = null
    ) : AuthState()

    /**
     * User is authenticated and in active session
     * @param email The authenticated user's email
     * @param sessionStartTime When the session started (System.currentTimeMillis())
     */
    data class Authenticated(
        val email: String,
        val sessionStartTime: Long
    ) : AuthState()
}