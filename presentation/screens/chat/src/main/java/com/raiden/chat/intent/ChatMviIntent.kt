package com.raiden.chat.intent

import com.raiden.chat.model.Action
import com.raiden.chat.model.State
import com.raiden.core.mvi.CoreMviIntent

class ChatMviIntent : CoreMviIntent<Action, State>() {
    override val initialState: State = State.Idle


    override fun bindActions() {

    }
}