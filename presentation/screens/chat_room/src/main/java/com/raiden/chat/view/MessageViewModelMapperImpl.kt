package com.raiden.chat.view

import com.raiden.chat.intent.MessageViewModelMapper
import com.raiden.chat.model.message.MessageViewModel
import com.raiden.chat.model.message.ReceiverMessageViewModel
import com.raiden.chat.model.message.SenderMessageViewModel
import com.raiden.domain.models.Message
import com.raiden.domain.models.User

class MessageViewModelMapperImpl : MessageViewModelMapper {
    override fun map(messags: List<Message>, selectedUserForChat: User): List<MessageViewModel> {
        val converterMessages = arrayListOf<MessageViewModel>()
        messags.forEach { message ->
            val messageViewModel = map(message, selectedUserForChat)
            converterMessages.add(messageViewModel)
        }
        return converterMessages
    }

    override fun map(message: Message, selectedUserForChat: User): MessageViewModel {
        return if (message.senderId == selectedUserForChat.id) {
            ReceiverMessageViewModel(message)
        } else {
            SenderMessageViewModel(message)
        }
    }
}