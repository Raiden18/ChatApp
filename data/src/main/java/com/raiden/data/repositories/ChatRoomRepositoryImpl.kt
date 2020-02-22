package com.raiden.data.repositories

import android.util.Log
import com.jakewharton.rxrelay2.BehaviorRelay
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

    override fun selectForChat(user: User): Observable<Nothing> {
        selectedUserForChat.accept(user)
        return Observable.empty()
    }

    override fun getSelectedUserForChat(): Observable<User> {
        return selectedUserForChat
    }

    override fun sendMessage(message: Message): Observable<Unit> {
        return qbUsersRxAdapter.createDialog(message.receiverId)
            .flatMap { qbUsersRxAdapter.getChatDialogById(it.dialogId) }
            .flatMap { dialog ->
                Log.i("1488-recipientId", dialog.recipientId.toString())
                Log.i("1488-rece", dialog.userId.toString())
                Log.i("1488-OCCUPANTS", dialog.occupants.toString())
                val qbMessage = message.mapToQuickBlox(dialog)
                qbUsersRxAdapter.sendMessage(qbMessage, dialog)
            }
            .toObservable()
            .map { Unit }
    }
}