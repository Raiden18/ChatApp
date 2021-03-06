package com.raiden.chat.model

import com.raiden.core.mvi.CoreAction

sealed class Action : CoreAction {
    object LoadData : Action()
    data class SendMessage(val text: String) : Action()
}