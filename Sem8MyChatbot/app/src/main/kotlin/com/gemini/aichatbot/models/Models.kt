package com.gemini.aichatbot.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.UUID

// ─────────────────────────────────────────────────────────
// Message Types
// ─────────────────────────────────────────────────────────

enum class MessageType {
    USER,       // Message sent by the user
    AI,         // Message from Gemini AI
    TYPING,     // Typing indicator placeholder
    ERROR       // Error message
}

enum class MessageStatus {
    SENDING,
    SENT,
    ERROR
}

// ─────────────────────────────────────────────────────────
// Chat Message Model
// ─────────────────────────────────────────────────────────

/**
 * Represents a single chat message in the conversation
 * @param id Unique identifier for the message
 * @param content The text content of the message
 * @param type Whether it's USER or AI message
 * @param timestamp Unix timestamp in milliseconds
 * @param sessionId The chat session this message belongs to
 * @param reaction Optional emoji reaction (👍, ❤️, etc.)
 */
@Entity(tableName = "messages")
data class ChatMessage(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val content: String,
    val type: MessageType,
    val timestamp: Long = System.currentTimeMillis(),
    val sessionId: String,
    val status: MessageStatus = MessageStatus.SENT,
    val reaction: String? = null,
    val isAnimating: Boolean = false  // For typing effect animation
)

// ─────────────────────────────────────────────────────────
// Chat Session Model
// ─────────────────────────────────────────────────────────

/**
 * Represents a chat conversation session
 */
@Entity(tableName = "chat_sessions")
data class ChatSession(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val createdAt: Long = System.currentTimeMillis(),
    val lastMessageAt: Long = System.currentTimeMillis(),
    val lastMessage: String = "",
    val messageCount: Int = 0
)

// ─────────────────────────────────────────────────────────
// Onboarding Page Model
// ─────────────────────────────────────────────────────────

data class OnboardingPage(
    val title: String,
    val description: String,
    val lottieAnimation: String,  // Lottie JSON file name in assets/raw
    val gradientStart: Int,       // Color resource
    val gradientEnd: Int          // Color resource
)

// ─────────────────────────────────────────────────────────
// UI State for ViewModel
// ─────────────────────────────────────────────────────────

sealed class ChatUiState {
    object Idle : ChatUiState()
    object Loading : ChatUiState()
    data class Success(val message: ChatMessage) : ChatUiState()
    data class Error(val errorMessage: String) : ChatUiState()
}

// ─────────────────────────────────────────────────────────
// Room Type Converters
// ─────────────────────────────────────────────────────────

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromMessageType(type: MessageType): String = type.name

    @TypeConverter
    fun toMessageType(value: String): MessageType = MessageType.valueOf(value)

    @TypeConverter
    fun fromMessageStatus(status: MessageStatus): String = status.name

    @TypeConverter
    fun toMessageStatus(value: String): MessageStatus = MessageStatus.valueOf(value)
}