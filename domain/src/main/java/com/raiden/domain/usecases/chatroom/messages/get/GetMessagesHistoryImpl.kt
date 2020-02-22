package com.raiden.domain.usecases.chatroom.messages.get

import com.raiden.domain.models.Message
import io.reactivex.Observable

class GetMessagesHistoryImpl :
    GetMessagesHistory {

    private val senderId = "123123"
    private val receiverId = "3213123"

    override fun invoke(): Observable<List<Message>> {
        val messages = listOf(
            createSenderMessage("Hello"),
            createReceiverMessage("Hello :)"),
            createSenderMessage("What is you up to?"),
            createReceiverMessage("Just messing around with chat app")
        )
        return Observable.just(messages)
    }

    private fun createSenderMessage(text: String) = Message(text, receiverId, senderId)

    private fun createReceiverMessage(text: String) = Message(text, senderId, receiverId)
}