package com.raiden.chat.view

import com.raiden.chat.intent.MessageViewModelMapper
import com.raiden.chat.model.message.MessageViewModel
import com.raiden.chat.model.message.ReceiverMessageViewModel
import com.raiden.chat.model.message.SenderMessageViewModel
import com.raiden.domain.models.Message
import com.raiden.domain.models.User

class MessageViewModelMapperImpl : MessageViewModelMapper {
    override fun map(
        message: Message,
        selectedUserForChat: User
    ): MessageViewModel {
        return if (message.receiverId == selectedUserForChat.id) {
            ReceiverMessageViewModel(message)
        } else {
            SenderMessageViewModel(message)
        }
    }
}