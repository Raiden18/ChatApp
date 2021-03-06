package com.raiden.login.models

import com.raiden.core.mvi.CoreState
import com.raiden.threestatestextinputlayout.FieldState

data class State(
    val logInState: FieldState = FieldState.EmptyState,
    val passwordState: FieldState = FieldState.EmptyState,
    val isButtonEnabled: Boolean = false,
    val isShowLoader: Boolean = false,
    val error: Throwable? = null
) : CoreState{
    override fun toString(): String {
        return "$logInState, $passwordState, $isButtonEnabled"
    }
}