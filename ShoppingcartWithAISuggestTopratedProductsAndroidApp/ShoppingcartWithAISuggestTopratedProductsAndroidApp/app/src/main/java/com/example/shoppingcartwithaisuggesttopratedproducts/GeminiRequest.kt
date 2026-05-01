package com.example.shoppingcartwithaisuggesttopratedproducts

// ---------- Gemini request/response ----------
data class GeminiRequest(val contents: List<GeminiContent>)
data class GeminiContent(val parts: List<GeminiPart>)
data class GeminiPart(val text: String)

// Only fields we need
data class GeminiResponse(val candidates: List<GeminiCandidate> = emptyList())
data class GeminiCandidate(val content: GeminiContent?)

// ---------- Product ----------
data class Product(
    val id: String,
    val name: String,
    val description: String,
    val rating: Double,
    val price: Double
)
