package com.example.geminiechatbot

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var chatHistory: TextView
    private lateinit var userInput: EditText
    private lateinit var sendButton: Button
    private lateinit var chatScroll: ScrollView

    private val apiKey = "AIzaSyBlHIesPtDzaru4s410kWAyGZ34kdNTfyw" // 🔑 Replace with your Gemini API key
    private val apiService by lazy { RetrofitClient.getClient(apiKey) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chatHistory = findViewById(R.id.chatHistory)
        userInput = findViewById(R.id.userInput)
        sendButton = findViewById(R.id.sendButton)
        chatScroll = findViewById(R.id.chatScrollView)
        sendButton.setOnClickListener {
            val userMessage = userInput.text.toString().trim()
            if (userMessage.isNotEmpty()) {
                appendChat("You: $userMessage\n")
                userInput.text.clear()
                sendMessage(userMessage)
            }
        }
    }

    private fun sendMessage(message: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = GeminiRequest(
                    contents = listOf(GeminiContent(parts = listOf(GeminiPart(message))))
                )

                val response = apiService.getChatResponse(request).execute()
                if (response.isSuccessful) {
                    val reply = response.body()?.candidates?.firstOrNull()
                        ?.content?.parts?.firstOrNull()?.text ?: "No reply"

                    withContext(Dispatchers.Main) {
                        appendChat("Bot: $reply\n")
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        appendChat("Error: ${response.code()} ${response.message()}\n")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    appendChat("Exception: ${e.message}\n")
                }
            }
        }
    }

    private fun appendChat(text: String) {
        chatHistory.append(text)
        chatScroll.post { chatScroll.fullScroll(ScrollView.FOCUS_DOWN) }
    }
}
