package com.gemini.aichatbot.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.gemini.aichatbot.models.*
import com.gemini.aichatbot.repository.GeminiDatabase
import com.gemini.aichatbot.repository.GeminiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID

/**
 * ViewModel for the main chat screen.
 * Manages conversation state, API calls, and UI state.
 * Survives configuration changes (e.g., screen rotation).
 */
class ChatViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GeminiRepository

    // Current active session ID
    private var currentSessionId: String = UUID.randomUUID().toString()

    // ─────────────────────────────────────────────────────
    // LiveData / StateFlow Observables
    // ─────────────────────────────────────────────────────

    // UI loading state
    private val _uiState = MutableLiveData<ChatUiState>(ChatUiState.Idle)
    val uiState: LiveData<ChatUiState> = _uiState

    // Whether AI is currently typing
    private val _isTyping = MutableLiveData<Boolean>(false)
    val isTyping: LiveData<Boolean> = _isTyping

    // Messages for current session
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages

    // In-memory conversation for API context
    private val conversationHistory = mutableListOf<ChatMessage>()

    // Error events (single-shot)
    private val _errorEvent = MutableLiveData<String?>()
    val errorEvent: LiveData<String?> = _errorEvent

    // Session title (updated after first message)
    private val _sessionTitle = MutableLiveData<String>("New Chat")
    val sessionTitle: LiveData<String> = _sessionTitle

    init {
        val database = GeminiDatabase.getDatabase(application)
        repository = GeminiRepository(database)

        // Create a new session on startup
        createNewSession()
    }

    // ─────────────────────────────────────────────────────
    // Session Management
    // ─────────────────────────────────────────────────────

    /**
     * Starts a fresh chat session
     */
    fun createNewSession() {
        currentSessionId = UUID.randomUUID().toString()
        conversationHistory.clear()
        _messages.value = emptyList()
        _isTyping.value = false
        _uiState.value = ChatUiState.Idle
        _sessionTitle.value = "New Chat"

        viewModelScope.launch {
            val session = ChatSession(
                id = currentSessionId,
                title = "New Chat",
                messageCount = 0
            )
            repository.insertSession(session)
        }

        // Observe messages for this session
        viewModelScope.launch {
            repository.getMessagesForSession(currentSessionId)
                .collect { messageList ->
                    _messages.value = messageList
                    conversationHistory.clear()
                    conversationHistory.addAll(messageList)
                }
        }
    }

    /**
     * Load an existing session by ID
     */
    fun loadSession(sessionId: String) {
        currentSessionId = sessionId
        conversationHistory.clear()
        _messages.value = emptyList()

        viewModelScope.launch {
            // Get session title
            repository.getSessionById(sessionId)?.let {
                _sessionTitle.value = it.title
            }

            // Observe messages
            repository.getMessagesForSession(sessionId)
                .collect { messageList ->
                    _messages.value = messageList
                    conversationHistory.clear()
                    conversationHistory.addAll(messageList)
                }
        }
    }

    // ─────────────────────────────────────────────────────
    // Sending Messages
    // ─────────────────────────────────────────────────────

    /**
     * Main function to send a user message and get AI response.
     * Handles: saving user msg → showing typing → calling API → saving AI msg
     */
    fun sendMessage(userText: String) {
        if (userText.isBlank()) return
        if (_uiState.value == ChatUiState.Loading) return

        viewModelScope.launch(Dispatchers.IO) {
            // 1. Create and save user message
            val userMessage = ChatMessage(
                content = userText.trim(),
                type = MessageType.USER,
                sessionId = currentSessionId,
                status = MessageStatus.SENT
            )
            repository.insertMessage(userMessage)

            // Update session title from first message
            if (conversationHistory.isEmpty()) {
                val title = if (userText.length > 40)
                    userText.take(40) + "…"
                else userText
                _sessionTitle.postValue(title)
                updateSessionTitle(title)
            }

            // 2. Show loading / typing state
            _uiState.postValue(ChatUiState.Loading)
            _isTyping.postValue(true)

            // 3. Call Gemini API
            val result = repository.sendMessage(
                conversationHistory = conversationHistory.toList(),
                userMessage = userText
            )

            // 4. Simulate minimum 500ms typing delay for realism
            delay(500)
            _isTyping.postValue(false)

            // 5. Handle result
            result.fold(
                onSuccess = { aiResponseText ->
                    val aiMessage = ChatMessage(
                        content = aiResponseText,
                        type = MessageType.AI,
                        sessionId = currentSessionId,
                        status = MessageStatus.SENT,
                        isAnimating = true  // Trigger typing animation
                    )
                    repository.insertMessage(aiMessage)
                    _uiState.postValue(ChatUiState.Success(aiMessage))

                    // Update session metadata
                    updateSessionMetadata(aiResponseText)
                },
                onFailure = { error ->
                    val errorMsg = when {
                        error.message?.contains("401") == true ->
                            "Invalid API key. Please check your Gemini API key in settings."
                        error.message?.contains("429") == true ->
                            "Rate limit exceeded. Please wait a moment before sending another message."
                        error.message?.contains("network") == true ||
                                error.message?.contains("UnknownHost") == true ->
                            "No internet connection. Please check your network."
                        else -> "Something went wrong: ${error.message ?: "Unknown error"}"
                    }

                    val errorMessage = ChatMessage(
                        content = errorMsg,
                        type = MessageType.ERROR,
                        sessionId = currentSessionId,
                        status = MessageStatus.ERROR
                    )
                    repository.insertMessage(errorMessage)
                    _uiState.postValue(ChatUiState.Error(errorMsg))
                    _errorEvent.postValue(errorMsg)
                }
            )
        }
    }

    /**
     * Regenerate the last AI response
     */
    fun regenerateLastResponse() {
        val messages = _messages.value ?: return
        val lastUserMsg = messages.lastOrNull { it.type == MessageType.USER } ?: return

        // Remove last AI message if it exists
        val lastAiMsg = messages.lastOrNull { it.type == MessageType.AI }
        if (lastAiMsg != null) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.deleteMessage(lastAiMsg)
                sendMessage(lastUserMsg.content)
            }
        }
    }

    /**
     * Add an emoji reaction to a message
     */
    fun reactToMessage(messageId: String, reaction: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val message = _messages.value?.find { it.id == messageId } ?: return@launch
            val updated = message.copy(reaction = if (message.reaction == reaction) null else reaction)
            repository.updateMessage(updated)
        }
    }

    /**
     * Clear all messages in current session
     */
    fun clearCurrentSession() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearSession(currentSessionId)
            conversationHistory.clear()
            _sessionTitle.postValue("New Chat")
        }
    }

    fun clearError() {
        _errorEvent.value = null
        _uiState.value = ChatUiState.Idle
    }

    // ─────────────────────────────────────────────────────
    // Private Helpers
    // ─────────────────────────────────────────────────────

    private suspend fun updateSessionTitle(title: String) {
        val session = repository.getSessionById(currentSessionId) ?: return
        repository.updateSession(session.copy(title = title))
    }

    private suspend fun updateSessionMetadata(lastMessage: String) {
        val session = repository.getSessionById(currentSessionId) ?: return
        repository.updateSession(
            session.copy(
                lastMessage = if (lastMessage.length > 60)
                    lastMessage.take(60) + "…" else lastMessage,
                lastMessageAt = System.currentTimeMillis(),
                messageCount = (_messages.value?.size ?: 0)
            )
        )
    }
}

// ─────────────────────────────────────────────────────────
// ViewModel Factory
// ─────────────────────────────────────────────────────────

class ChatViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}