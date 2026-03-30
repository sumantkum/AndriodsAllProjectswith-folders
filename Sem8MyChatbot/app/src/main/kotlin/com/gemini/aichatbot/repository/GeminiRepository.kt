package com.gemini.aichatbot.repository

import com.gemini.aichatbot.BuildConfig
import com.gemini.aichatbot.api.*
import com.gemini.aichatbot.models.ChatMessage
import com.gemini.aichatbot.models.ChatSession
import com.gemini.aichatbot.models.MessageType
import kotlinx.coroutines.flow.Flow

/**
 * Repository layer — single source of truth for all data.
 * Bridges the ViewModel with the Gemini API and local Room database.
 */
class GeminiRepository(
    private val database: GeminiDatabase
) {

    private val apiService = RetrofitClient.geminiApiService
    private val apiKey = BuildConfig.GEMINI_API_KEY

    // ─────────────────────────────────────────────────────
    // Gemini API Calls
    // ─────────────────────────────────────────────────────

    /**
     * Send a conversation to Gemini and return AI text response.
     * Builds full multi-turn conversation context for coherent replies.
     *
     * @param conversationHistory List of previous messages for context
     * @param userMessage The new user message to send
     * @return Result wrapping the AI response text or an error
     */
    suspend fun sendMessage(
        conversationHistory: List<ChatMessage>,
        userMessage: String
    ): Result<String> {
        return try {
            // Convert chat history to Gemini API format
            val contents = conversationHistory
                .filter { it.type == MessageType.USER || it.type == MessageType.AI }
                .map { message ->
                    Content(
                        role = if (message.type == MessageType.USER) "user" else "model",
                        parts = listOf(Part(message.content))
                    )
                }.toMutableList()

            // Add current user message
            contents.add(
                Content(
                    role = "user",
                    parts = listOf(Part(userMessage))
                )
            )

            val request = GeminiRequest(
                contents = contents,
                generationConfig = GenerationConfig(
                    temperature = 0.9f,
                    maxOutputTokens = 2048
                )
            )

            val response = apiService.generateContent(
                model = "gemini-1.5-flash",
                apiKey = apiKey,
                request = request
            )

            if (response.isSuccessful) {
                val body = response.body()
                val aiText = body?.candidates?.firstOrNull()
                    ?.content?.parts?.firstOrNull()?.text
                    ?: "I couldn't generate a response. Please try again."

                Result.success(aiText)
            } else {
                val errorBody = response.errorBody()?.string()
                Result.failure(Exception("API Error ${response.code()}: $errorBody"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ─────────────────────────────────────────────────────
    // Message Database Operations
    // ─────────────────────────────────────────────────────

    fun getMessagesForSession(sessionId: String): Flow<List<ChatMessage>> =
        database.messageDao().getMessagesForSession(sessionId)

    suspend fun insertMessage(message: ChatMessage) =
        database.messageDao().insertMessage(message)

    suspend fun deleteMessage(message: ChatMessage) =
        database.messageDao().deleteMessage(message)

    suspend fun updateMessage(message: ChatMessage) =
        database.messageDao().updateMessage(message)

    suspend fun clearSession(sessionId: String) =
        database.messageDao().deleteAllMessagesForSession(sessionId)

    // ─────────────────────────────────────────────────────
    // Session Database Operations
    // ─────────────────────────────────────────────────────

    fun getAllSessions(): Flow<List<ChatSession>> =
        database.sessionDao().getAllSessions()

    suspend fun insertSession(session: ChatSession) =
        database.sessionDao().insertSession(session)

    suspend fun updateSession(session: ChatSession) =
        database.sessionDao().updateSession(session)

    suspend fun deleteSession(session: ChatSession) {
        database.sessionDao().deleteSession(session)
        database.messageDao().deleteAllMessagesForSession(session.id)
    }

    suspend fun getSessionById(id: String): ChatSession? =
        database.sessionDao().getSessionById(id)
}