package com.gemini.aichatbot.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit interface for Google Gemini API
 * Base URL: https://generativelanguage.googleapis.com/v1beta/
 */
interface GeminiApiService {

    @POST("models/{model}:generateContent")
    suspend fun generateContent(
        @Path("model") model: String = "gemini-1.5-flash",
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): Response<GeminiResponse>
}


// -----------------------------
// Request Models
// -----------------------------

data class GeminiRequest(
    val contents: List<Content>,
    val generationConfig: GenerationConfig = GenerationConfig()
)

data class Content(
    val role: String,
    val parts: List<Part>
)

data class Part(
    val text: String
)

data class GenerationConfig(
    val temperature: Float = 0.9f,
    val topK: Int = 1,
    val topP: Float = 1.0f,
    val maxOutputTokens: Int = 2048
)


// -----------------------------
// Response Models
// -----------------------------

data class GeminiResponse(
    val candidates: List<Candidate>?,
    val error: GeminiError?
)

data class Candidate(
    val content: Content?,
    val finishReason: String?
)

data class GeminiError(
    val code: Int,
    val message: String,
    val status: String
)