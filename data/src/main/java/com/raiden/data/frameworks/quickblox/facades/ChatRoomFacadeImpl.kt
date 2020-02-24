package com.raiden.data.frameworks.quickblox.facades

import android.util.Log
import com.quickblox.chat.QBChatService
import com.quickblox.chat.model.QBChatDialog
import com.quickblox.chat.request.QBMessageGetBuilder
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
    private lateinit var qbChatDialog: QBChatDialog


    override fun sendMessage(message: Message): Completable {
        qbChatDialog.initForChat(QBChatService.getInstance())
        val qbMessage = message.ToQB()
        qbMessage.setSaveToHistory(true)
        qbMessage.dialogId = qbChatDialog.dialogId
        return qbRxAdapter.sendMessage(qbMessage, qbChatDialog)
            .doOnComplete { Log.i("HUI", "SEND") }
    }

    override fun getAllMessages(receiverId: Int): Observable<List<Message>> {
        val messageGetBuilder = QBMessageGetBuilder()
        messageGetBuilder.limit = 500
        return qbRxAdapter.createDialog(receiverId)
            .doOnSuccess { qbChatDialog = it }
            .flatMap { qbRxAdapter.getAllMessages(messageGetBuilder, it) }
            .map { it.map { it.toDomain() } }
            .toObservable()
    }

    override fun subscribeOnIncomingMessages(): Observable<Message> {
        return Observable.empty()/*Observable.just(qbChatDialog)
            .onErrorResumeNext { error: Throwable -> Observable.empty<QBChatDialog>() }
            .flatMap { qbRxAdapter.subscribeOnIncomingMessages(it) }
            .map { it.toDomain() }*/
    }

    override fun getCurrentDialog(): Observable<Dialog> {
        val dialog = qbChatDialog.toDomain()
        return Observable.just(dialog)
    }
}