package com.raiden.chat.view

import android.util.Log
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
            val messageViewModel = if (message.senderId == selectedUserForChat.id) {
                Log.i("HUI", "CREATE RECEIVER")
                ReceiverMessageViewModel(message)
            } else {
                Log.i("HUI", "CREATE SENDER")
                SenderMessageViewModel(message)
            }
            converterMessages.add(messageViewModel)
        }

        return converterMessages
    }


}