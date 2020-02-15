package com.raiden.login.intent

import com.raiden.core.mvi.CoreMviIntent
import com.raiden.core.mvi.Reducer
import com.raiden.domain.validation.strategies.LoginValidationStrategy
import com.raiden.domain.validation.strategies.PasswordValidationStrategy
import com.raiden.domain.validation.strategies.ValidationStrategy
import com.raiden.login.models.Action
import com.raiden.login.models.Change
import com.raiden.login.models.State
import com.raiden.threestatestextinputlayout.FieldState
import com.raiden.threestatestextinputlayout.FieldValidator
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.plusAssign

class MviIntent : CoreMviIntent<Action, State>() {
    override val initialState: State = State()

    private val reducer: Reducer<State, Change> = { state, change ->
        when (change) {
            is Change.Login -> state.copy(logInState = change.fieldState)
            is Change.Password -> state.copy(passwordState = change.fieldState)
            is Change.LogInButton -> state.copy(isButtonEnabled = change.isEnabled)
        }
    }
    private val fieldValidator = FieldValidator()

    init {
        bindActions()
    }

    override fun bindActions() {
        val loginInput = actions.ofType<Action.InputLogIn>()
            .map { it.logIn }
            .map { LoginValidationStrategy(it) }
            .map { validateField(it) }
            .map { Change.Login(it) }

        val passwordInput = actions.ofType<Action.InputPassword>()
            .map { it.password }
            .map { PasswordValidationStrategy(it) }
            .map { validateField(it) }
            .map { Change.Password(it) }

        val buttonState = Observable.combineLatest(
            loginInput,
            passwordInput,
            BiFunction { logIn: Change.Login, password: Change.Password ->
                return@BiFunction logIn.isFieldValid() && password.isFieldValid()
            })
            .map { Change.LogInButton(it) }

        disposables += Observable.merge(loginInput, passwordInput, buttonState)
            .scan(initialState, reducer)
            .distinctUntilChanged()
            .subscribe(state::setValue)
    }

    private fun validateField(validationStrategy: ValidationStrategy): FieldState {
        fieldValidator.setStrategy(validationStrategy)
        return fieldValidator.validate()
    }
}