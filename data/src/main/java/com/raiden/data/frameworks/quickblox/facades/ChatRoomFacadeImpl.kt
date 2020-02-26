package com.raiden.data.frameworks.quickblox.facades

import android.util.Log
import com.jakewharton.rxrelay2.BehaviorRelay
import com.quickblox.auth.session.QBSessionManager
import com.quickblox.chat.QBChatService
import com.quickblox.chat.model.QBChatDialog
import com.quickblox.chat.request.QBMessageGetBuilder
import com.quickblox.chat.utils.DialogUtils
import com.raiden.data.frameworks.quickblox.adapters.QBRxAdapter
import com.raiden.data.frameworks.quickblox.mappers.ToQB
import com.raiden.data.frameworks.quickblox.mappers.toDomain
import com.raiden.data.repositories.chatroom.ChatRoomFacade
import com.raiden.domain.models.Dialog
import com.raiden.domain.models.Message
import io.reactivex.Completable
import io.reactivex.Observable


class ChatRoomFacadeImpl(
    private val qbRxAdapter: QBRxAdapter
) : ChatRoomFacade {
    private val qbChatDialog = BehaviorRelay.create<QBChatDialog>()

    override fun sendMessage(message: Message): Completable {
        val qbMessage = message.ToQB()
        qbMessage.senderId = QBSessionManager.getInstance().sessionParameters.userId
        qbMessage.setSaveToHistory(true)
        return qbChatDialog
            .flatMapCompletable { qbRxAdapter.sendMessage(qbMessage, it) }
    }

    override fun getAllMessages(receiverId: Int): Observable<List<Message>> {
        val messageGetBuilder = QBMessageGetBuilder()
        messageGetBuilder.limit = 500
        val dialog = DialogUtils.buildPrivateDialog(receiverId)
        return qbRxAdapter.createDialog(dialog)
            .doOnSuccess { it.initForChat(QBChatService.getInstance()) }
            .doOnSuccess { qbChatDialog.accept(it) }
            .flatMap { qbRxAdapter.getAllMessages(messageGetBuilder, it) }
            .map { it.map { it.toDomain() } }
            .toObservable()
    }

    override fun subscribeOnIncomingMessages(): Observable<Message> {
        Log.i("HUI", QBChatService.getInstance().isLoggedIn.toString())
        val sessionParameters = QBSessionManager.getInstance().sessionParameters
        Log.i("HUI-ID", sessionParameters.userId.toString())
        Log.i("HUI-EMAIL", sessionParameters.userEmail.toString())
        return qbChatDialog
            .doOnNext { Log.i("HUI", it.dialogId) }
            .concatMap { qbRxAdapter.subscribeOnIncomingMessages(it) }
            .map { it.toDomain() }
    }

    override fun getCurrentDialog(): Observable<Dialog> {
        return qbChatDialog.map { it.toDomain() }
    }
}