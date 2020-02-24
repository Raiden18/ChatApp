package com.raiden.data.repositories

import android.util.Log
import com.jakewharton.rxrelay2.BehaviorRelay
import com.quickblox.chat.exception.QBChatException
import com.quickblox.chat.listeners.QBChatDialogMessageListener
import com.quickblox.chat.model.QBChatDialog
import com.quickblox.chat.model.QBChatMessage
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
            .flatMap { qbUsersRxAdapter.getAllMessages(it) }
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

    override fun subscribeOnIncomingMessages(): Observable<Message> {
        return currentChatDialog
            .switchIfEmpty { Observable.empty<QBChatDialog>() }
            .flatMap { message -> qbUsersRxAdapter.subscribeOnIncomingMessages(message) }
            .map {
                Message(
                    it.body,
                    it.senderId,
                    it.recipientId
                )
            }

    }
}