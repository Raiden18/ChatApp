package com.raiden.data.frameworks.quickblox.mappers

import com.quickblox.chat.model.QBChatDialog
import com.quickblox.chat.model.QBChatMessage
import com.raiden.domain.models.Message
import java.util.*

fun Message.mapToQuickBlox(dialog: QBChatDialog): QBChatMessage {
    return QBChatMessage().apply {
        body = text
        this.dialogId = dialog.dialogId
        recipientId = dialog.recipientId
        senderId = dialog.userId
        dateSent = Calendar.getInstance().timeInMillis
        deliveredIds = dialog.occupants.toList()
    }
}