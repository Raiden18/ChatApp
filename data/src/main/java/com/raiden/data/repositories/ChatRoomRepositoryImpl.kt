package com.raiden.data.repositories

import com.jakewharton.rxrelay2.BehaviorRelay
import com.raiden.domain.models.Message
import com.raiden.domain.models.User
import com.raiden.domain.repositories.ChatRoomRepository
import io.reactivex.Observable

class ChatRoomRepositoryImpl : ChatRoomRepository {
    private val selectedUserForChat = BehaviorRelay.create<User>()

    override fun selectForChat(user: User): Observable<Nothing> {
        selectedUserForChat.accept(user)
        return Observable.empty()
    }

    override fun getSelectedUserForChat(): Observable<User> {
        return selectedUserForChat
    }

    override fun sendMessage(message: Message): Observable<Nothing> {
        return Observable.empty()
    }
}