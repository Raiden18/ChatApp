package com.raiden.login.models

import com.raiden.threestatestextinputlayout.FieldState

sealed class Change {
    data class Login(val fieldState: FieldState) : Change() {
        fun isFieldValid() = fieldState == FieldState.ValidState
    }

    data class Password(val fieldState: FieldState) : Change() {
        fun isFieldValid() = fieldState == FieldState.ValidState
    }

    data class LogInButtonState(val isEnabled: Boolean) : Change()

    data class LogInError(val error: Throwable) : Change()

    object ShowLoader : Change()
    object HideLoading : Change()
}