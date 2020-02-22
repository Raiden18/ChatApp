package com.raiden.domain.usecases.chatroom.messages.send

import com.raiden.domain.models.Message
import com.raiden.domain.repositories.ChatRoomRepository
import io.reactivex.Observable

class SendMessageImpl(
    private val chatRoomRepository: ChatRoomRepository
) : SendMessage {

    override fun invoke(text: String): Observable<Nothing> {
        return chatRoomRepository.sendMessage(Message("", "", ""))
    }
}