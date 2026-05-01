package com.example.shoppingcartwithaisuggesttopratedproducts

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("v1beta/models/gemini-2.5-flash:generateContent")
    fun generateContent(@Body body: GeminiRequest): Call<GeminiResponse>
}
