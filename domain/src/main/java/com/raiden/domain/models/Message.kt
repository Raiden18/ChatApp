package com.raiden.domain.models

data class Message(
    val text: String,
    val senderId: Int,
    val receiverId: Int,
    val dialogId: String
)