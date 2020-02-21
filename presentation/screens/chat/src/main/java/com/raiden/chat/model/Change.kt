package com.raiden.chat.model

import com.raiden.domain.models.Message

sealed class Change {
    data class ShowMessages(val messages: List<Message>) : Change()
    object ShowLoader : Change()
}