package com.raiden.chat.model.message

import com.raiden.domain.models.Message

data class SenderMessageViewModel(
    val message: Message
) : MessageViewModel