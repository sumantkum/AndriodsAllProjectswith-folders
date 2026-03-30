package com.gemini.aichatbot.activities

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gemini.aichatbot.R
import com.gemini.aichatbot.adapters.ChatAdapter
import com.gemini.aichatbot.databinding.ActivityMainBinding
import com.gemini.aichatbot.models.ChatMessage
import com.gemini.aichatbot.models.ChatUiState
import com.gemini.aichatbot.utils.PreferencesManager
import com.gemini.aichatbot.utils.gone
import com.gemini.aichatbot.utils.hideKeyboard
import com.gemini.aichatbot.utils.visible
import com.gemini.aichatbot.utils.vibrateShort
import com.gemini.aichatbot.viewmodel.ChatViewModel
import com.gemini.aichatbot.viewmodel.ChatViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

/**
 * Main chat activity — the heart of the app.
 * Handles message sending/receiving, voice input, UI state, and animations.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ChatViewModel by viewModels {
        ChatViewModelFactory(application)
    }
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var prefs: PreferencesManager
    private var isRecording = false

    // Voice input launcher
    private val speechLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val text = result.data
                ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                ?.firstOrNull() ?: return@registerForActivityResult
            binding.etMessage.setText(text)
            binding.etMessage.setSelection(text.length)
        }
    }

    // Mic permission launcher
    private val micPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) startVoiceInput()
        else Toast.makeText(this, "Microphone permission needed for voice input", Toast.LENGTH_SHORT).show()
    }

    // ─────────────────────────────────────────────────────
    // Lifecycle
    // ─────────────────────────────────────────────────────

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = PreferencesManager(this)

        setupRecyclerView()
        setupInputArea()
        setupToolbar()
        observeViewModel()
        startTypingIndicatorAnimation()
    }

    // ─────────────────────────────────────────────────────
    // Setup Methods
    // ─────────────────────────────────────────────────────

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter(
            onCopyClick = { message -> copyToClipboard(message) },
            onShareClick = { message -> shareMessage(message) },
            onReactionClick = { message, reaction -> viewModel.reactToMessage(message.id, reaction) },
            onRegenerateClick = { viewModel.regenerateLastResponse() }
        )

        binding.rvMessages.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(this@MainActivity).apply {
                stackFromEnd = true
            }
            setHasFixedSize(false)
        }
    }

    private fun setupInputArea() {
        // Text change listener: show/hide send button and voice button
        binding.etMessage.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val hasText = !s.isNullOrBlank()
                binding.btnSend.visibility = if (hasText) View.VISIBLE else View.GONE
                binding.btnVoice.visibility = if (hasText) View.GONE else View.VISIBLE
                animateSendButton(hasText)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Send button click
        binding.btnSend.setOnClickListener {
            val text = binding.etMessage.text.toString().trim()
            if (text.isNotEmpty()) {
                vibrateShort()
                animateSendButtonPress()
                viewModel.sendMessage(text)
                binding.etMessage.text?.clear()
                it.hideKeyboard()
            }
        }

        // Voice input button
        binding.btnVoice.setOnClickListener {
            handleVoiceInput()
        }

        // Emoji button
        binding.btnEmoji.setOnClickListener {
            showEmojiPanel()
        }
    }

    private fun setupToolbar() {
        // Session title
        viewModel.sessionTitle.observe(this) { title ->
            binding.tvChatTitle.text = title
        }

        // New chat FAB
        binding.fabNewChat.setOnClickListener {
            animateFab()
            viewModel.createNewSession()
            Toast.makeText(this, "New conversation started", Toast.LENGTH_SHORT).show()
        }

        // History button
        binding.btnHistory.setOnClickListener {
            startActivity(Intent(this, ChatHistoryActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        // Settings button
        binding.btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    // ─────────────────────────────────────────────────────
    // ViewModel Observers
    // ─────────────────────────────────────────────────────

    private fun observeViewModel() {
        // Observe message list
        lifecycleScope.launch {
            viewModel.messages.collectLatest { messages ->
                chatAdapter.submitList(messages) {
                    // Auto-scroll to bottom when new message arrives
                    if (messages.isNotEmpty()) {
                        binding.rvMessages.smoothScrollToPosition(messages.size - 1)
                    }
                }

                // Show/hide empty state
                if (messages.isEmpty()) {
                    binding.layoutEmptyState.visible()
                    binding.rvMessages.gone()
                } else {
                    binding.layoutEmptyState.gone()
                    binding.rvMessages.visible()
                }
            }
        }

        // Observe UI state (loading / success / error)
        viewModel.uiState.observe(this) { state ->
            when (state) {
                is ChatUiState.Loading -> {
                    binding.layoutTypingIndicator.visible()
                    binding.btnSend.isEnabled = false
                }
                is ChatUiState.Success -> {
                    binding.layoutTypingIndicator.gone()
                    binding.btnSend.isEnabled = true
                }
                is ChatUiState.Error -> {
                    binding.layoutTypingIndicator.gone()
                    binding.btnSend.isEnabled = true
                    showErrorSnackbar(state.errorMessage)
                }
                is ChatUiState.Idle -> {
                    binding.layoutTypingIndicator.gone()
                    binding.btnSend.isEnabled = true
                }
            }
        }

        // Observe typing state
        viewModel.isTyping.observe(this) { typing ->
            if (typing) {
                binding.layoutTypingIndicator.visible()
                binding.typingAnimation.playAnimation()
            } else {
                binding.layoutTypingIndicator.gone()
                binding.typingAnimation.pauseAnimation()
            }
        }
    }

    // ─────────────────────────────────────────────────────
    // UI Animations
    // ─────────────────────────────────────────────────────

    private fun animateSendButton(show: Boolean) {
        if (show) {
            binding.btnSend.scaleX = 0f
            binding.btnSend.scaleY = 0f
            binding.btnSend.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(200)
                .setInterpolator(android.view.animation.OvershootInterpolator())
                .start()
        }
    }

    private fun animateSendButtonPress() {
        binding.btnSend.animate()
            .scaleX(0.85f)
            .scaleY(0.85f)
            .setDuration(80)
            .withEndAction {
                binding.btnSend.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(80)
                    .start()
            }.start()
    }

    private fun animateFab() {
        binding.fabNewChat.animate()
            .rotation(360f)
            .setDuration(400)
            .withEndAction {
                binding.fabNewChat.rotation = 0f
            }.start()
    }

    private fun startTypingIndicatorAnimation() {
        // The three-dot typing indicator is a Lottie animation in the layout
        binding.typingAnimation.setAnimation(R.raw.typing_dots_animation)
    }

    // ─────────────────────────────────────────────────────
    // Voice Input
    // ─────────────────────────────────────────────────────

    private fun handleVoiceInput() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            == PackageManager.PERMISSION_GRANTED) {
            startVoiceInput()
        } else {
            micPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    private fun startVoiceInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Ask Gemini anything...")
        }
        try {
            speechLauncher.launch(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Voice input not supported on this device", Toast.LENGTH_SHORT).show()
        }
    }

    // ─────────────────────────────────────────────────────
    // Message Actions
    // ─────────────────────────────────────────────────────

    private fun copyToClipboard(message: ChatMessage) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("AI Response", message.content)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show()
        vibrateShort()
    }

    private fun shareMessage(message: ChatMessage) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "Gemini AI said:\n\n${message.content}\n\n— Shared via Gemini AI Chatbot")
        }
        startActivity(Intent.createChooser(shareIntent, "Share response"))
    }

    private fun showEmojiPanel() {
        // In a real app, implement a custom emoji keyboard here
        // For now, show a simple bottom sheet with common emojis
        val emojis = listOf("😊", "🤔", "👍", "❤️", "🚀", "✨", "💡", "🎉", "🔥", "😂")
        val emojiString = emojis.joinToString(" ")
        Toast.makeText(this, "Emoji panel: $emojiString", Toast.LENGTH_SHORT).show()
    }

    private fun showErrorSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(this, R.color.error_bg))
            .setTextColor(ContextCompat.getColor(this, R.color.error_text))
            .setAction("Retry") {
                viewModel.clearError()
            }
            .show()
    }
}