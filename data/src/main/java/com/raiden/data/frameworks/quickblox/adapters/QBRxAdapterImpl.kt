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
                    QBUsers.signIn(qbUser).performAsync(object : QBEntityCallback<QBUser> {
                        override fun onSuccess(p0: QBUser, p1: Bundle?) {
                            emitter.onSuccess(p0)
                        }

                        override fun onError(p0: QBResponseException?) {
                            emitter.onError(p0!!)
                        }
                    })
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

    override fun createDialog(qbChatDialog: QBChatDialog): Single<QBChatDialog> {
        return Single.create { emitter ->
            val quickBoxRxAdapter = SimpleSingleEntityCallback(emitter)
            QBRestChatService.createChatDialog(qbChatDialog).performAsync(quickBoxRxAdapter)
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
            val qbUser = QBUser(email, password, email)
            QBAuth.createSessionByEmail(qbUser).performAsync(object : QBEntityCallback<QBSession> {
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
            QBChatService.getInstance().incomingMessagesManager.addDialogMessageListener(object :
                QBChatDialogMessageListener {
                override fun processMessage(p0: String?, p1: QBChatMessage?, p2: Int?) {
                    Log.i("HUI", "MESSAGE")
                }

                override fun processError(
                    p0: String?,
                    p1: QBChatException?,
                    p2: QBChatMessage?,
                    p3: Int?
                ) {

                }
            })
        }
    }
}


