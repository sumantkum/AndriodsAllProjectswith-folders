package com.example.geminiechatbot

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

// Request models
data class GeminiRequest(val contents: List<GeminiContent>)
data class GeminiContent(val parts: List<GeminiPart>)
data class GeminiPart(val text: String)

// Response models
data class GeminiResponse(val candidates: List<GeminiCandidate>)
data class GeminiCandidate(val content: GeminiContent)

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("v1beta/models/gemini-2.5-flash:generateContent")
    fun getChatResponse(@Body request: GeminiRequest): Call<GeminiResponse>
}
