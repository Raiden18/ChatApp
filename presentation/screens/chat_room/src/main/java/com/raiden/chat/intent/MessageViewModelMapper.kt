package com.raiden.chat.intent

import com.raiden.chat.model.message.MessageViewModel
import com.raiden.domain.models.Message
import com.raiden.domain.models.User

interface MessageViewModelMapper {
    fun map(message: Message, selectedUserForChat: User): MessageViewModel
}