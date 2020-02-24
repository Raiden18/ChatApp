package com.raiden.data.repositories.chatroom

import com.jakewharton.rxrelay2.BehaviorRelay
import com.raiden.domain.models.Dialog
import com.raiden.domain.models.Message
import com.raiden.domain.models.User
import com.raiden.domain.repositories.ChatRoomRepository
import io.reactivex.Completable
import io.reactivex.Observable

class ChatRoomRepositoryImpl(
    private val chatFacade: ChatRoomFacade
) : ChatRoomRepository {
    private val selectedUserForChat = BehaviorRelay.create<User>()

    override fun selectForChat(user: User): Observable<Nothing> {
        selectedUserForChat.accept(user)
        return Observable.empty()
    }

    override fun getSelectedUserForChat(): Observable<User> {
        return selectedUserForChat
    }

    override fun sendMessage(message: Message): Completable {
        return chatFacade.sendMessage(message)
    }

    override fun getAllMessages(receiverId: Int): Observable<List<Message>> {
        return chatFacade.getAllMessages(receiverId)
    }

    override fun subscribeOnIncomingMessages(): Observable<Message> {
        return  chatFacade.subscribeOnIncomingMessages()
    }

    override fun getCurrentDialog(): Observable<Dialog> {
        return chatFacade.getCurrentDialog()
    }
}