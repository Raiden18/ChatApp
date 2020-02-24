package com.raiden.data.frameworks.quickblox.mappers

import com.quickblox.chat.model.QBChatDialog
import com.raiden.domain.models.Dialog

internal fun Dialog.toQB(): QBChatDialog {
    val qbDialog = QBChatDialog()
    qbDialog.dialogId = this.id
    qbDialog.userId = userId
    qbDialog.setOccupantsIds(occupantIds)
    return qbDialog
}

internal fun QBChatDialog.toDomain(): Dialog {
    return Dialog(
        dialogId,
        userId,
        occupants
    )

}
