package com.raiden.domain.usecases.chatroom.messages.get

import com.raiden.domain.models.Message
import com.raiden.domain.repositories.ChatRoomRepository
import io.reactivex.Observable

class GetMessagesHistoryImpl(
    private val chatRoomRepository: ChatRoomRepository
) : GetMessagesHistory {

    override fun invoke(): Observable<List<Message>> {
        return chatRoomRepository.getSelectedUserForChat()
            .flatMap { chatRoomRepository.getAllMessages(it.id) }
    }
}