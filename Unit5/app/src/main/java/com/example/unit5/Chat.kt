package com.example.unit5

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unit5.databinding.ActivityChatBinding
import java.util.UUID

class Chat : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var messageAdapter: MessageAdapter
    private val messages = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupClickListeners()
        simulateWelcomeMessage()
    }

    private fun setupRecyclerView() {
        messageAdapter = MessageAdapter(messages)
        binding.recyclerViewMessages.apply {
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(this@Chat)
        }
    }

    private fun setupClickListeners() {
        binding.buttonSend.setOnClickListener {
            val messageText = binding.editTextMessage.text.toString().trim()
            if (messageText.isNotEmpty()) {
                sendMessage(messageText)
            }
        }
    }

    private fun sendMessage(text: String) {
        val message = Message(
            id = UUID.randomUUID().toString(),
            text = text,
            isSentByUser = true
        )

        messages.add(message)
        messageAdapter.notifyItemInserted(messages.size - 1)
        binding.recyclerViewMessages.smoothScrollToPosition(messages.size - 1)
        binding.editTextMessage.text.clear()

        // Simulate support response
        simulateSupportResponse()
    }

    private fun simulateWelcomeMessage() {
        val welcomeMessage = Message(
            id = UUID.randomUUID().toString(),
            text = "Hello! How can we help you today?",
            isSentByUser = false
        )
        messages.add(welcomeMessage)
        messageAdapter.notifyItemInserted(messages.size - 1)
    }

    private fun simulateSupportResponse() {
        Handler(Looper.getMainLooper()).postDelayed({
            val responseMessage = Message(
                id = UUID.randomUUID().toString(),
                text = "Thank you for your message. Our support team will get back to you shortly.",
                isSentByUser = false
            )
            messages.add(responseMessage)
            messageAdapter.notifyItemInserted(messages.size - 1)
            binding.recyclerViewMessages.smoothScrollToPosition(messages.size - 1)
        }, 1500)
    }
}