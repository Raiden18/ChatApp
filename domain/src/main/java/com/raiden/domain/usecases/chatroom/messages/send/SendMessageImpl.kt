package com.raiden.domain.usecases.chatroom.messages.send

import com.raiden.domain.models.Dialog
import com.raiden.domain.models.Message
import com.raiden.domain.models.User
import com.raiden.domain.repositories.ChatRoomRepository
import com.raiden.domain.repositories.SessionRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.functions.Function3

class SendMessageImpl(
    private val chatRoomRepository: ChatRoomRepository,
    private val sessionRepository: SessionRepository
) : SendMessage {

    override fun invoke(text: String): Completable {
        return Observable.zip(
            sessionRepository.getUser(),
            chatRoomRepository.getSelectedUserForChat(),
            chatRoomRepository.getCurrentDialog(),
            Function3<User, User, Dialog, Message> { senderUser, receiverUser, dialog ->
                return@Function3 Message(
                    text = text,
                    receiverId = receiverUser.id,
                    senderId = senderUser.id,
                    dialogId = dialog.id
                )
            })
            .flatMapCompletable { chatRoomRepository.sendMessage(it) }
    }
}