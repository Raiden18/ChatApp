package com.raiden.chat.model.message

import com.raiden.domain.models.Message

interface MessageViewModel {
    fun getMessage(): Message
}