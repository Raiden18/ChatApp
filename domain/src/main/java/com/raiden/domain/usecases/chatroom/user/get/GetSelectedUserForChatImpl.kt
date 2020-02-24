package com.raiden.domain.usecases.chatroom.user.get

import com.raiden.domain.models.User
import com.raiden.domain.repositories.ChatRoomRepository
import io.reactivex.Observable

class GetSelectedUserForChatImpl(
    private val chatRoomRepository: ChatRoomRepository
) : GetSelectedUserForChat {

    override fun invoke(): Observable<User> {
        return chatRoomRepository.getSelectedUserForChat()
    }
}