package com.raiden.data.frameworks.quickblox.adapters

import com.quickblox.chat.model.QBChatDialog
import com.quickblox.chat.model.QBChatMessage
import com.quickblox.users.model.QBUser
import io.reactivex.Observable
import io.reactivex.Single

interface QBUsersRxAdapter {
    fun logIn(qbUser: QBUser): Single<QBUser>

    fun createSession(email: String, password: String): Single<QBUser>

    fun getUsers(page: Int, perPage: Int): Single<ArrayList<QBUser>>

    fun createDialog(receiverId: Int): Single<QBChatDialog>

    fun sendMessage(message: QBChatMessage, chatDialog: QBChatDialog): Single<Unit>

    fun getChatDialogById(dialogId: String): Single<QBChatDialog>

    fun getUserById(id: Int): Single<QBUser>

    fun getAllMessages(qbChatDialog: QBChatDialog): Single<ArrayList<QBChatMessage>>

    fun subscribeOnIncomingMessages(qbChatDialog: QBChatDialog): Observable<QBChatMessage>
}