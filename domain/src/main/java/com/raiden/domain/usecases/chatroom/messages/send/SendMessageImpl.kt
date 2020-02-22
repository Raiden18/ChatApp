package com.raiden.domain.usecases.chatroom.messages.send

import com.raiden.domain.models.Message
import com.raiden.domain.models.User
import com.raiden.domain.repositories.ChatRoomRepository
import com.raiden.domain.repositories.SessionRepository
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class SendMessageImpl(
    private val chatRoomRepository: ChatRoomRepository,
    private val sessionRepository: SessionRepository
) : SendMessage {

    override fun invoke(text: String): Observable<Unit> {
        return Observable.zip(
            sessionRepository.getUser(),
            chatRoomRepository.getSelectedUserForChat(),
            BiFunction<User, User, Message> { senderUser, receiverUser ->
                return@BiFunction Message(
                    text = text,
                    receiverId = receiverUser.id,
                    senderId = senderUser.id
                )
            })
            .flatMap { chatRoomRepository.sendMessage(it) }
    }
}