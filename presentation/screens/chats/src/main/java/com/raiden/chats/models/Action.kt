package com.raiden.chats.models

import com.raiden.core.mvi.CoreAction

sealed class Action : CoreAction {
    object LoadChats : Action()
    object OpenSearch : Action()
    object GoBack: Action()
}