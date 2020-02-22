package com.raiden.domain.usecases.chatroom.user.select

import com.raiden.domain.models.User
import com.raiden.domain.repositories.ChatRoomRepository
import io.reactivex.Observable

class SelectUserForChatUseCaseImpl(
    private val chatRomRepository: ChatRoomRepository
) : SelectUserForChatUseCase {

    override fun invoke(user: User): Observable<Nothing> {
        return chatRomRepository.selectForChat(user)
    }
}