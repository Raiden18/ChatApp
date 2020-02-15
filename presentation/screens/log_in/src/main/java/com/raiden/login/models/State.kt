package com.raiden.login.models

import com.raiden.core.mvi.CoreState
import com.raiden.threestatestextinputlayout.FieldState

data class State(
    val logInState: FieldState = FieldState.EmptyState,
    val passwordState: FieldState = FieldState.EmptyState,
    val isButtonEnabled: Boolean = false
) : CoreState