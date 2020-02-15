package com.raiden.login.models

import com.raiden.core.mvi.CoreAction

sealed class Action : CoreAction {
    object ShowEmpty : Action()
    class InputLogIn(val logIn: String) : Action()
    class InputPassword(val password: String) : Action()
    object LogIn : Action()
}