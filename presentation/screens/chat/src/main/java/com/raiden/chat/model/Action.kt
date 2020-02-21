package com.raiden.chat.model

import com.raiden.core.mvi.CoreAction

sealed class Action: CoreAction {
    object LoadDialogHistory: Action()
}