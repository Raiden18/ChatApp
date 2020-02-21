package com.raiden.domain.models

data class Message(
    val text: String,
    val senderId: String,
    val receiverId: String
)