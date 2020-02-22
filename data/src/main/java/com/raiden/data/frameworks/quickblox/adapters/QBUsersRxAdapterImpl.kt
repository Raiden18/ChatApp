package com.raiden.data.frameworks.quickblox.adapters

import android.os.Bundle
import com.quickblox.auth.QBAuth
import com.quickblox.auth.session.QBSession
import com.quickblox.chat.QBChatService
import com.quickblox.chat.QBRestChatService
import com.quickblox.chat.model.QBChatDialog
import com.quickblox.chat.model.QBChatMessage
import com.quickblox.chat.utils.DialogUtils
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import com.quickblox.core.request.QBPagedRequestBuilder
import com.quickblox.users.QBUsers
import com.quickblox.users.model.QBUser
import com.raiden.data.frameworks.quickblox.adapters.utils.SimpleSingleEntityCallback
import io.reactivex.Single

class QBUsersRxAdapterImpl : QBUsersRxAdapter {

    override fun logIn(qbUser: QBUser): Single<QBUser> {
        return Single.create { emitter ->
            QBChatService.getInstance().login(qbUser, object : QBEntityCallback<Void> {
                override fun onSuccess(p0: Void?, p1: Bundle?) {
                    emitter.onSuccess(qbUser)
                }

                override fun onError(p0: QBResponseException?) {
                    emitter.onError(p0!!)
                }
            })
        }
    }

    override fun getUsers(page: Int, perPage: Int): Single<ArrayList<QBUser>> {
        return Single.create { emitter ->
            val qbPagedRequestBuilder = QBPagedRequestBuilder().apply {
                setPerPage(perPage)
                setPage(page)
            }
            val callback = SimpleSingleEntityCallback(emitter)
            QBUsers.getUsers(qbPagedRequestBuilder).performAsync(callback)
        }
    }

    override fun createDialog(receiverId: Int): Single<QBChatDialog> {
        return Single.create { emitter ->
            val dialog = DialogUtils.buildPrivateDialog(receiverId)
            val quickBoxRxAdapter = SimpleSingleEntityCallback(emitter)
            QBRestChatService.createChatDialog(dialog).performAsync(quickBoxRxAdapter)
        }
    }

    override fun sendMessage(
        message: QBChatMessage,
        chatDialog: QBChatDialog
    ): Single<Void> {
        return Single.create { emitter ->
            chatDialog.initForChat(QBChatService.getInstance())
            message.setSaveToHistory(true)
            chatDialog.deliverMessage(message)
            val quickBoxRxAdapter = SimpleSingleEntityCallback(emitter)
            chatDialog.sendMessage(message, quickBoxRxAdapter)
        }
    }

    override fun createSession(email: String, password: String): Single<QBSession> {
        return Single.create { emitter ->
            val qbUser = QBUser(email, password)
            val quickBoxRxAdapter = SimpleSingleEntityCallback(emitter)
            QBAuth.createSession(qbUser).performAsync(quickBoxRxAdapter)
        }
    }

    override fun getChatDialogById(dialogId: String): Single<QBChatDialog> {
        return Single.create { emitter ->
            val quickBoxRxAdapter = SimpleSingleEntityCallback(emitter)
            QBRestChatService.getChatDialogById(dialogId).performAsync(quickBoxRxAdapter)

        }
    }

    override fun getUserById(id: Int): Single<QBUser> {
        return Single.create { emitter ->
            val quickBoxRxAdapter = SimpleSingleEntityCallback(emitter)
            QBUsers.getUser(id).performAsync(quickBoxRxAdapter)
        }
    }
}