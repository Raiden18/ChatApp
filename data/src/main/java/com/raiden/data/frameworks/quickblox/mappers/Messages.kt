package com.raiden.data.frameworks.quickblox.mappers

import com.quickblox.chat.model.QBChatMessage
import com.raiden.domain.models.Message

internal fun QBChatMessage.toDomain(): Message {
    return Message(
        body,
        senderId,
        recipientId,
        dialogId
    )

}

internal fun Message.ToQB(): QBChatMessage {
    val qbMessage = QBChatMessage()
    qbMessage.body = text
    qbMessage.recipientId = receiverId
    qbMessage.senderId = senderId
    return qbMessage
}