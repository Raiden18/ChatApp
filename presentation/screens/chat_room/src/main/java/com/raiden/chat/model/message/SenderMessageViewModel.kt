package com.raiden.chat.model.message

import com.raiden.domain.models.Message

data class SenderMessageViewModel(
    private val message: Message
) : MessageViewModel {
    override fun getMessage(): Message {
        return message
    }

}