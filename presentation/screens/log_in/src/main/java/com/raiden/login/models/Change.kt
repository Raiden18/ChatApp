package com.raiden.login.models

import com.raiden.threestatestextinputlayout.FieldState

sealed class Change {
    data class Login(val fieldState: FieldState) : Change() {
        fun isFieldValid() = fieldState == FieldState.ValidState
    }

    data class Password(val fieldState: FieldState) : Change() {
        fun isFieldValid() = fieldState == FieldState.ValidState
    }

    data class LogInButton(val isEnabled: Boolean) : Change()
}