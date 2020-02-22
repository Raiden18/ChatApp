package com.raiden.data.repositories

import com.jakewharton.rxrelay2.BehaviorRelay
import com.quickblox.chat.model.QBChatDialog
import com.raiden.data.frameworks.quickblox.adapters.QBUsersRxAdapter
import com.raiden.data.frameworks.quickblox.mappers.mapToQuickBlox
import com.raiden.domain.models.Message
import com.raiden.domain.models.User
import com.raiden.domain.repositories.ChatRoomRepository
import io.reactivex.Observable

class ChatRoomRepositoryImpl(
    private val qbUsersRxAdapter: QBUsersRxAdapter
) : ChatRoomRepository {
    private val selectedUserForChat = BehaviorRelay.create<User>()
    private val currentChatDialog = BehaviorRelay.create<QBChatDialog>()

    override fun selectForChat(user: User): Observable<Nothing> {
        selectedUserForChat.accept(user)
        return Observable.empty()
    }

    override fun getSelectedUserForChat(): Observable<User> {
        return selectedUserForChat
    }

    override fun sendMessage(message: Message): Observable<Unit> {
        return currentChatDialog
            .flatMap { dialog ->
                val qbMessage = message.mapToQuickBlox(dialog)
                qbUsersRxAdapter.sendMessage(qbMessage, dialog)
                    .toObservable()
            }
            .map { Unit }
    }

    override fun getAllMessages(receiverId: Int): Observable<List<Message>> {
        return qbUsersRxAdapter.createDialog(receiverId)
            .doOnSuccess { currentChatDialog.accept(it) }
            .flatMap { qbUsersRxAdapter.getAllMessages(it.dialogId) }
            .flatMapObservable { Observable.fromIterable(it) }
            .map {
                Message(
                    it.body,
                    it.senderId,
                    it.recipientId
                )
            }
            .toList()
            .toObservable()
    }
}