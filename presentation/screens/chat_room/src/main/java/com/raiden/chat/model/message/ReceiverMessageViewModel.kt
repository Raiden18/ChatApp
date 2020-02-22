package com.raiden.chat.model.message

import com.raiden.domain.models.Message

data class ReceiverMessageViewModel(
    val message: Message
) : MessageViewModel