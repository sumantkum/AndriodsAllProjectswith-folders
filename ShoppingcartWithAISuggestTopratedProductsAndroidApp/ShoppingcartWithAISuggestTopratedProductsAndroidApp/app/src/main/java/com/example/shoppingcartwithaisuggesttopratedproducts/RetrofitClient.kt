package com.example.shoppingcartwithaisuggesttopratedproducts

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"
    private const val GEMINI_API_KEY = "AIzaSyBlHIesPtDzaru4s410kWAyGZ34kdNTfyw" // <-- put your key here (AIza...)

    private val authQueryInterceptor = Interceptor { chain ->
        val newUrl = chain.request().url.newBuilder()
            .addQueryParameter("key", GEMINI_API_KEY) // Gemini expects ?key=...
            .build()
        val newReq = chain.request().newBuilder().url(newUrl).build()
        chain.proceed(newReq)
    }

    private val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(authQueryInterceptor)
        .addInterceptor(logger)
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
