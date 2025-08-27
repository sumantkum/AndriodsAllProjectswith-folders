package com.example.unit5

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.Locale
import android.view.ViewGroup
import android.view.LayoutInflater


class MessageAdapter(private val messages: List<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_USER = 1
        private const val VIEW_TYPE_SUPPORT = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_USER) {
            UserMessageViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_user_message, parent, false)
            )
        } else {
            SupportMessageViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_support_message, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        when (holder) {
            is UserMessageViewHolder -> holder.bind(message)
            is SupportMessageViewHolder -> holder.bind(message)
        }
    }

    override fun getItemCount() = messages.size

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isSentByUser) VIEW_TYPE_USER else VIEW_TYPE_SUPPORT
    }

    class UserMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageText: TextView = itemView.findViewById(R.id.text_message_body)
        private val timeText: TextView = itemView.findViewById(R.id.text_message_time)

        fun bind(message: Message) {
            messageText.text = message.text
            timeText.text = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(message.timestamp)
        }
    }

    class SupportMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageText: TextView = itemView.findViewById(R.id.text_message_body)
        private val timeText: TextView = itemView.findViewById(R.id.text_message_time)
        private val agentImage: CircleImageView = itemView.findViewById(R.id.image_agent)

        fun bind(message: Message) {
            messageText.text = message.text
            timeText.text = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(message.timestamp)
            Glide.with(itemView)
                .load(R.drawable.img)
                .into(agentImage)
        }
    }
}