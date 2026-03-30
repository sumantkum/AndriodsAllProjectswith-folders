package com.gemini.aichatbot.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.animation.core.animate
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gemini.aichatbot.databinding.ItemChatHistoryBinding
import com.gemini.aichatbot.models.ChatSession
import java.text.SimpleDateFormat
import java.util.*

/**
 * Adapter for the chat history list.
 */
class ChatHistoryAdapter(
    private val onSessionClick: (ChatSession) -> Unit,
    private val onDeleteClick: (ChatSession) -> Unit
) : ListAdapter<ChatSession, ChatHistoryAdapter.HistoryViewHolder>(SessionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemChatHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HistoryViewHolder(
        private val binding: ItemChatHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(session: ChatSession) {
            binding.apply {
                tvTitle.text = session.title
                tvPreview.text = session.lastMessage.ifEmpty { "Empty conversation" }
                tvDate.text = formatDate(session.lastMessageAt)
                tvMessageCount.text = "${session.messageCount} messages"

                root.setOnClickListener {
                    animateCard()
                    onSessionClick(session)
                }
                btnDelete.setOnClickListener { onDeleteClick(session) }
            }
        }

        private fun animateCard() {
            binding.root.animate()
                .scaleX(0.97f)
                .scaleY(0.97f)
                .setDuration(80)
                .withEndAction {
                    binding.root.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(80)
                        .start()
                }.start()
        }

        private fun formatDate(timestamp: Long): String {
            val now = System.currentTimeMillis()
            val diff = now - timestamp
            return when {
                diff < 60_000 -> "Just now"
                diff < 3_600_000 -> "${diff / 60_000}m ago"
                diff < 86_400_000 -> "${diff / 3_600_000}h ago"
                else -> SimpleDateFormat("MMM dd", Locale.getDefault()).format(Date(timestamp))
            }
        }
    }
}

class SessionDiffCallback : DiffUtil.ItemCallback<ChatSession>() {
    override fun areItemsTheSame(a: ChatSession, b: ChatSession) = a.id == b.id
    override fun areContentsTheSame(a: ChatSession, b: ChatSession) = a == b
}