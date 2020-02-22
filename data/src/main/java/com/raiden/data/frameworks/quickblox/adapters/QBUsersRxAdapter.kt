package com.raiden.data.frameworks.quickblox.adapters

import com.quickblox.chat.model.QBChatDialog
import com.quickblox.chat.model.QBChatMessage
import com.quickblox.users.model.QBUser
import io.reactivex.Single

interface QBUsersRxAdapter {
    fun logIn(qbUser: QBUser): Single<QBUser>

    fun createSession(qbUser: QBUser): Single<QBUser>

    fun getUsers(page: Int, perPage: Int): Single<ArrayList<QBUser>>

    fun createDialog(receiverId: Int): Single<QBChatDialog>

    fun sendMessage(message: QBChatMessage, chatDialog: QBChatDialog): Single<Void>

    fun getChatDialogById(dialogId: String): Single<QBChatDialog>

    fun getUserById(id: Int): Single<QBUser>
}