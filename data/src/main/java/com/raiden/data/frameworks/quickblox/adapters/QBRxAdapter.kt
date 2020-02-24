package com.raiden.data.frameworks.quickblox.adapters

import com.quickblox.chat.model.QBChatDialog
import com.quickblox.chat.model.QBChatMessage
import com.quickblox.chat.request.QBMessageGetBuilder
import com.quickblox.users.model.QBUser
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface QBRxAdapter {
    fun logIn(qbUser: QBUser): Single<QBUser>

    fun createSession(email: String, password: String): Single<QBUser>

    fun getUsers(page: Int, perPage: Int): Single<ArrayList<QBUser>>

    fun createDialog(receiverId: Int): Single<QBChatDialog>

    fun sendMessage(message: QBChatMessage, chatDialog: QBChatDialog): Completable

    fun getChatDialogById(dialogId: String): Single<QBChatDialog>

    fun getUserById(id: Int): Single<QBUser>

    fun getAllMessages(
        qBMessageGetBuilder: QBMessageGetBuilder,
        qbChatDialog: QBChatDialog
    ): Single<ArrayList<QBChatMessage>>

    fun subscribeOnIncomingMessages(qbChatDialog: QBChatDialog): Observable<QBChatMessage>
}