package com.raiden.data.frameworks.quickblox.adapters

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.quickblox.auth.QBAuth
import com.quickblox.auth.session.QBSession
import com.quickblox.chat.QBChatService
import com.quickblox.chat.QBRestChatService
import com.quickblox.chat.QBSystemMessagesManager
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
import io.reactivex.Observable
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
    ): Single<Unit> {
        return Single.create { emitter ->
            chatDialog.initForChat(QBChatService.getInstance())
            message.setSaveToHistory(true)
            chatDialog.deliverMessage(message)
            chatDialog.sendMessage(message, object : QBEntityCallback<Void> {
                override fun onSuccess(p0: Void?, p1: Bundle?) {
                    emitter.onSuccess(Unit)
                }

                override fun onError(p0: QBResponseException?) {
                    emitter.onError(p0!!)
                }
            })
            QBRestChatService.createMessage(message, true)
                .performAsync(object : QBEntityCallback<QBChatMessage> {
                    override fun onSuccess(p0: QBChatMessage?, p1: Bundle?) {
                        emitter.onSuccess(Unit)
                    }

                    override fun onError(p0: QBResponseException) {
                        emitter.onError(p0)
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

    override fun getAllMessages(qbChatDialog: QBChatDialog): Single<ArrayList<QBChatMessage>> {
        return Single.create { emitter ->
            val messageGetBuilder = QBMessageGetBuilder()
            messageGetBuilder.limit = 500
            val quickBoxRxAdapter = SimpleSingleEntityCallback(emitter)
            QBRestChatService.getDialogMessages(qbChatDialog, messageGetBuilder)
                .performAsync(quickBoxRxAdapter)
        }
    }

    override fun subscribeOnIncomingMessages(qbChatDialog: QBChatDialog): Observable<QBChatMessage> {
        return Observable.create { emitter ->
            QBChatService.getInstance().incomingMessagesManager.addDialogMessageListener(object :
                QBChatDialogMessageListener {
                override fun processMessage(p0: String?, p1: QBChatMessage?, p2: Int?) {
                    Log.i("HUI", p1!!.body.toString())
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

val PROPERTY_OCCUPANTS_IDS = "occupants_ids"
val PROPERTY_DIALOG_TYPE = "dialog_type"
val PROPERTY_DIALOG_NAME = "dialog_name"
val PROPERTY_NOTIFICATION_TYPE = "notification_type"


private fun buildSystemMessageAboutCreatingGroupDialog(dialog: QBChatDialog): QBChatMessage {
    val qbChatMessage = QBChatMessage()
    qbChatMessage.dialogId = dialog.dialogId
    qbChatMessage.setProperty(
        PROPERTY_OCCUPANTS_IDS,
        getOccupantsIdsStringFromList(dialog.occupants)
    )
    qbChatMessage.setProperty(PROPERTY_DIALOG_TYPE, dialog.type.code.toString())
    qbChatMessage.setProperty(PROPERTY_DIALOG_NAME, dialog.name.toString())
    qbChatMessage.setProperty(PROPERTY_NOTIFICATION_TYPE, "1")

    return qbChatMessage
}

fun getOccupantsIdsStringFromList(occupantIdsList: Collection<Int>): String {
    return TextUtils.join(",", occupantIdsList)
}

fun sendSystemMessageAboutCreatingDialog(
    systemMessagesManager: QBSystemMessagesManager,
    dialog: QBChatDialog
) {
    val systemMessageCreatingDialog = buildSystemMessageAboutCreatingGroupDialog(dialog)

    for (recipientId in dialog.occupants) {
        if (recipientId != QBChatService.getInstance().user.id) {
            systemMessageCreatingDialog.recipientId = recipientId
            systemMessagesManager.sendSystemMessage(systemMessageCreatingDialog)
        }
    }
}

