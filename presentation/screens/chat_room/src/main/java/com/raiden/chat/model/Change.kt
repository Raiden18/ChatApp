package com.raiden.chat.model

import com.raiden.chat.model.message.MessageViewModel

sealed class Change {
    data class ShowMessages(val messages: List<MessageViewModel>) : Change()
    data class ShowIncomingMessage(val message: MessageViewModel) : Change()
    object ShowLoader : Change()
    object DoNothing : Change()
}