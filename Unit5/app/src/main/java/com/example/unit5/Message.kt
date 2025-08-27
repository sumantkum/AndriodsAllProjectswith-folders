package com.example.unit5

data class Message(
    val id: String = "",
    val text: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isSentByUser: Boolean = true
)