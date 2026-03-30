package com.gemini.aichatbot.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sem8mychatbot.databinding.ActivityChatHistoryBinding
import com.gemini.aichatbot.R
import com.gemini.aichatbot.adapters.ChatHistoryAdapter
import com.gemini.aichatbot.databinding.ActivityChatHistoryBinding
import com.gemini.aichatbot.models.ChatSession
import com.gemini.aichatbot.repository.GeminiDatabase
import com.gemini.aichatbot.repository.GeminiRepository
import com.gemini.aichatbot.utils.gone
import com.gemini.aichatbot.utils.visible
import com.gemini.aichatbot.viewmodel.ChatViewModel
import com.gemini.aichatbot.viewmodel.ChatViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Shows a list of all past chat sessions.
 * Users can tap to resume a conversation or delete old ones.
 */
class ChatHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatHistoryBinding
    private lateinit var historyAdapter: ChatHistoryAdapter
    private lateinit var repository: GeminiRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = GeminiRepository(GeminiDatabase.getDatabase(this))

        setupRecyclerView()
        observeSessions()
        setupToolbar()
    }

    private fun setupToolbar() {
        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        historyAdapter = ChatHistoryAdapter(
            onSessionClick = { session -> openSession(session) },
            onDeleteClick = { session -> confirmDelete(session) }
        )

        binding.rvHistory.apply {
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(this@ChatHistoryActivity)
        }
    }

    private fun observeSessions() {
        lifecycleScope.launch {
            repository.getAllSessions().collectLatest { sessions ->
                historyAdapter.submitList(sessions)

                if (sessions.isEmpty()) {
                    binding.layoutEmpty.visible()
                    binding.rvHistory.gone()
                } else {
                    binding.layoutEmpty.gone()
                    binding.rvHistory.visible()
                }
            }
        }
    }

    private fun openSession(session: ChatSession) {
        // Pass session ID back to MainActivity
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("SESSION_ID", session.id)
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        finish()
    }

    private fun confirmDelete(session: ChatSession) {
        AlertDialog.Builder(this)
            .setTitle("Delete Conversation")
            .setMessage("Are you sure you want to delete \"${session.title}\"? This cannot be undone.")
            .setPositiveButton("Delete") { _, _ ->
                lifecycleScope.launch {
                    repository.deleteSession(session)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}