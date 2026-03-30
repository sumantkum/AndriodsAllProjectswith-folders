package com.gemini.aichatbot.adapters

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gemini.aichatbot.R
import com.gemini.aichatbot.databinding.ItemMessageAiBinding
import com.gemini.aichatbot.databinding.ItemMessageUserBinding
import com.gemini.aichatbot.models.ChatMessage
import com.gemini.aichatbot.models.MessageType
import kotlinx.coroutines.*

/**
 * RecyclerView adapter for displaying chat messages.
 * Supports two view types: USER messages (right-aligned) and AI messages (left-aligned).
 * Features animated message entry and AI typing effect.
 */
class ChatAdapter(
    private val onCopyClick: (ChatMessage) -> Unit,
    private val onShareClick: (ChatMessage) -> Unit,
    private val onReactionClick: (ChatMessage, String) -> Unit,
    private val onRegenerateClick: (ChatMessage) -> Unit
) : ListAdapter<ChatMessage, RecyclerView.ViewHolder>(MessageDiffCallback()) {

    companion object {
        private const val VIEW_TYPE_USER = 0
        private const val VIEW_TYPE_AI = 1
        private const val TYPING_SPEED_MS = 18L  // Milliseconds per character for typing effect
    }

    private val animatedIds = mutableSetOf<String>()
    private val typingJobs = mutableMapOf<String, Job>()

    // ─────────────────────────────────────────────────────
    // ViewHolder Creation
    // ─────────────────────────────────────────────────────

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).type) {
            MessageType.USER -> VIEW_TYPE_USER
            else -> VIEW_TYPE_AI
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_USER -> {
                val binding = ItemMessageUserBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                UserMessageViewHolder(binding)
            }
            else -> {
                val binding = ItemMessageAiBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                AiMessageViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        when (holder) {
            is UserMessageViewHolder -> holder.bind(message)
            is AiMessageViewHolder -> holder.bind(message)
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        if (holder is AiMessageViewHolder) {
            // Cancel typing animation if the view is recycled
            typingJobs[holder.currentMessageId]?.cancel()
        }
    }

    // ─────────────────────────────────────────────────────
    // User Message ViewHolder
    // ─────────────────────────────────────────────────────

    inner class UserMessageViewHolder(
        private val binding: ItemMessageUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(message: ChatMessage) {
            binding.apply {
                tvMessage.text = message.content
                tvTimestamp.text = formatTime(message.timestamp)

                // Show reaction if exists
                if (message.reaction != null) {
                    tvReaction.visibility = View.VISIBLE
                    tvReaction.text = message.reaction
                } else {
                    tvReaction.visibility = View.GONE
                }
            }

            // Animate new messages sliding in from the right
            if (!animatedIds.contains(message.id)) {
                animatedIds.add(message.id)
                animateSlideIn(binding.root, fromRight = true)
            }
        }
    }

    // ─────────────────────────────────────────────────────
    // AI Message ViewHolder
    // ─────────────────────────────────────────────────────

    inner class AiMessageViewHolder(
        private val binding: ItemMessageAiBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        var currentMessageId: String = ""

        fun bind(message: ChatMessage) {
            currentMessageId = message.id

            binding.apply {
                // Error message styling
                if (message.type == MessageType.ERROR) {
                    cardView.setCardBackgroundColor(
                        ContextCompat.getColor(root.context, R.color.error_bg)
                    )
                    tvMessage.setTextColor(
                        ContextCompat.getColor(root.context, R.color.error_text)
                    )
                } else {
                    cardView.setCardBackgroundColor(
                        ContextCompat.getColor(root.context, R.color.ai_bubble_bg)
                    )
                    tvMessage.setTextColor(
                        ContextCompat.getColor(root.context, R.color.text_primary)
                    )
                }

                tvTimestamp.text = formatTime(message.timestamp)

                // Reaction display
                if (message.reaction != null) {
                    tvReaction.visibility = View.VISIBLE
                    tvReaction.text = message.reaction
                } else {
                    tvReaction.visibility = View.GONE
                }

                // Typing animation for new AI messages
                if (message.isAnimating && !animatedIds.contains(message.id)) {
                    animatedIds.add(message.id)
                    animateSlideIn(binding.root, fromRight = false)
                    startTypingAnimation(message)
                } else {
                    tvMessage.text = message.content
                }

                // Action buttons
                btnCopy.setOnClickListener {
                    onCopyClick(message)
                    animateButtonPress(btnCopy)
                }
                btnShare.setOnClickListener {
                    onShareClick(message)
                    animateButtonPress(btnShare)
                }
                btnRegenerate.setOnClickListener {
                    onRegenerateClick(message)
                    animateButtonPress(btnRegenerate)
                }

                // Reaction buttons
                btnReactThumb.setOnClickListener { onReactionClick(message, "👍") }
                btnReactHeart.setOnClickListener { onReactionClick(message, "❤️") }
                btnReactStar.setOnClickListener { onReactionClick(message, "⭐") }
            }
        }

        /**
         * Animate text appearing character by character (typewriter effect)
         */
        private fun startTypingAnimation(message: ChatMessage) {
            typingJobs[message.id]?.cancel()
            typingJobs[message.id] = CoroutineScope(Dispatchers.Main).launch {
                binding.tvMessage.text = ""
                val fullText = message.content
                for (i in fullText.indices) {
                    if (!isActive) break
                    binding.tvMessage.text = fullText.substring(0, i + 1)
                    delay(TYPING_SPEED_MS)
                }
            }
        }
    }

    // ─────────────────────────────────────────────────────
    // Animation Helpers
    // ─────────────────────────────────────────────────────

    private fun animateSlideIn(view: View, fromRight: Boolean) {
        val translationX = if (fromRight) 200f else -200f
        view.translationX = translationX
        view.alpha = 0f
        view.animate()
            .translationX(0f)
            .alpha(1f)
            .setDuration(350)
            .setInterpolator(OvershootInterpolator(0.8f))
            .start()
    }

    private fun animateButtonPress(view: View) {
        view.animate()
            .scaleX(0.8f)
            .scaleY(0.8f)
            .setDuration(100)
            .withEndAction {
                view.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(100)
                    .start()
            }.start()
    }

    private fun formatTime(timestamp: Long): String {
        val sdf = java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault())
        return sdf.format(java.util.Date(timestamp))
    }
}

// ─────────────────────────────────────────────────────────
// DiffUtil Callback
// ─────────────────────────────────────────────────────────

class MessageDiffCallback : DiffUtil.ItemCallback<ChatMessage>() {
    override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage) =
        oldItem == newItem
}