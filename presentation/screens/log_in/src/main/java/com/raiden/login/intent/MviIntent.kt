package com.raiden.login.intent

import com.raiden.core.mvi.CoreMviIntent
import com.raiden.core.mvi.Reducer
import com.raiden.login.models.Action
import com.raiden.login.models.Change
import com.raiden.login.models.State
import com.raiden.threestatestextinputlayout.FieldState
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.plusAssign

class MviIntent : CoreMviIntent<Action, State>() {
    override val initialState: State = State()

    private val reducer: Reducer<State, Change> = { state, change ->
        when (change) {
            is Change.Login -> state.copy(logInState = change.fieldState)
            is Change.Password -> state.copy(passowordState = change.fieldState)
            is Change.LogInButton -> state.copy(isButtonEnabled = change.isEnabled)
        }
    }

    init {
        bindActions()
    }

    override fun bindActions() {
        val logInInput = actions.ofType<Action.InputLogIn>()
            .map { it.logIn }
            .map { validateField(it) }
            .map { Change.Login(it) }

        val passwordInput = actions.ofType<Action.InputPassword>()
            .map { it.password }
            .map { validateField(it) }
            .map { Change.Password(it) }

        val buttonEnabled = Observable.combineLatest(
            logInInput,
            passwordInput,
            BiFunction { logIn: Change.Login, password: Change.Password ->
                return@BiFunction logIn.isFieldValid() && password.isFieldValid()
            })
            .map { Change.LogInButton(it) }

        val allChanges = Observable.merge(logInInput, passwordInput, buttonEnabled)

        disposables += allChanges
            .scan(initialState, reducer)
            .distinctUntilChanged()
            .subscribe(state::setValue)

    }

    private fun validateField(field: String): FieldState {
        return when {
            field.isEmpty() -> FieldState.EmptyState
            field.length <= 3 -> FieldState.InvalidState
            else -> FieldState.ValidState
        }
    }
}