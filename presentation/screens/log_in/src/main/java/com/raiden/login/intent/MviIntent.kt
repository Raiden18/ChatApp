package com.raiden.login.intent

import com.raiden.core.mvi.CoreMviIntent
import com.raiden.login.models.Action
import com.raiden.login.models.State

class MviIntent : CoreMviIntent<Action, State>() {
    override val initialState: State = State("")
}