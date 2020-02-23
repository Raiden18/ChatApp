package com.raiden.domain.usecases.chatroom.messages.subscribe

import com.raiden.domain.models.Message
import com.raiden.domain.repositories.ChatRoomRepository
import io.reactivex.Observable

class SubscribeOnIncomingMessagesImpl(
    private val chatRoomRepository: ChatRoomRepository
) : SubscribeOnIncomingMessages {

    override fun invoke(): Observable<Message> {
        return chatRoomRepository.subscribeOnIncomingMessages()
    }
}