package com.raiden.chat.intent

import com.raiden.chat.model.message.MessageViewModel
import com.raiden.domain.models.Message

interface MessageViewModelMapper {
    fun map(message: Message): MessageViewModel
}