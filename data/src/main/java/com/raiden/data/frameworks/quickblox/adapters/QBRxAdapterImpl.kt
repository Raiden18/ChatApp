package com.raiden.data.frameworks.quickblox.adapters

import android.os.Bundle
import android.util.Log
import com.quickblox.auth.QBAuth
import com.quickblox.auth.session.QBSession
import com.quickblox.chat.QBChatService
import com.quickblox.chat.QBRestChatService
import com.quickblox.chat.exception.QBChatException
import com.quickblox.chat.listeners.QBChatDialogMessageListener
import com.quickblox.chat.model.QBChatDialog
import com.quickblox.chat.model.QBChatMessage
import com.quickblox.chat.request.QBMessageGetBuilder
import com.quickblox.chat.utils.DialogUtils
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import com.quickblox.core.request.QBPagedRequestBuilder
import com.quickblox.users.QBUsers
import com.quickblox.users.model.QBUser
import com.raiden.data.frameworks.quickblox.adapters.utils.SimpleSingleEntityCallback
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class QBRxAdapterImpl : QBRxAdapter {

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
    ): Completable {
        return Completable.create { emitter ->
            chatDialog.sendMessage(message, object : QBEntityCallback<Void> {
                override fun onSuccess(p0: Void?, p1: Bundle?) {
                    emitter.onComplete()
                }

                override fun onError(p0: QBResponseException?) {
                    emitter.onError(p0!!)
                }
            })
        }
    }

    override fun createSession(email: String, password: String): Single<QBUser> {
        return Single.create { emitter ->
            val qbUser = QBUser(email, password)
            QBAuth.createSession(qbUser).performAsync(object : QBEntityCallback<QBSession> {
                override fun onSuccess(p0: QBSession, p1: Bundle?) {
                    qbUser.id = p0.userId
                    emitter.onSuccess(qbUser)
                }

                override fun onError(p0: QBResponseException) {
                    emitter.onError(p0)
                }
            })
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

    override fun getAllMessages(
        qBMessageGetBuilder: QBMessageGetBuilder,
        qbChatDialog: QBChatDialog
    ): Single<ArrayList<QBChatMessage>> {
        return Single.create { emitter ->
            val quickBoxRxAdapter = SimpleSingleEntityCallback(emitter)
            QBRestChatService.getDialogMessages(qbChatDialog, qBMessageGetBuilder)
                .performAsync(quickBoxRxAdapter)
        }

    }

    override fun subscribeOnIncomingMessages(qbChatDialog: QBChatDialog): Observable<QBChatMessage> {
        return Observable.create { emitter ->
            Log.i("HUI", "SUBSCRIBE")
            qbChatDialog.addMessageListener(object : QBChatDialogMessageListener {
                override fun processMessage(p0: String?, p1: QBChatMessage, p2: Int?) {
                    Log.i("HUI", p1.body)
                    emitter.onNext(p1)
                }

                override fun processError(
                    p0: String?,
                    p1: QBChatException,
                    p2: QBChatMessage?,
                    p3: Int?
                ) {
                    emitter.onError(p1)
                }
            })
        }
    }
}


