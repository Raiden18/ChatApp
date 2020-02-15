package com.raiden.login.intent

import com.raiden.core.mvi.CoreMviIntent
import com.raiden.core.mvi.Reducer
import com.raiden.domain.usecases.login.LogInUseCase
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
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MviIntent(
    private val logInUseCase: LogInUseCase,
    private val logInEventListener: LogInEventListener
) : CoreMviIntent<Action, State>() {
    override val initialState: State = State()

    private val reducer: Reducer<State, Change> = { state, change ->
        when (change) {
            is Change.Login -> state.copy(
                logInState = change.fieldState,
                error = null,
                isShowLoader = false
            )
            is Change.Password -> state.copy(
                passwordState = change.fieldState,
                error = null,
                isShowLoader = false
            )
            is Change.LogInButtonState -> state.copy(
                isButtonEnabled = change.isEnabled,
                error = null,
                isShowLoader = false
            )
            is Change.LogInError -> state.copy(error = change.error, isShowLoader = false)
            is Change.ShowLoader -> state.copy(error = null, isShowLoader = true)
            is Change.HideLoading -> state.copy(error = null, isShowLoader = false)
        }
    }
    private val fieldValidator = FieldValidator()
    private lateinit var email: String
    private lateinit var password: String

    init {
        bindActions()
    }

    override fun bindActions() {
        val emailInput = actions.ofType<Action.InputLogIn>()
            .map { it.email }
            .doOnNext { email = it }
            .map { LoginValidationStrategy(it) }
            .map { validateField(it) }
            .map { Change.Login(it) }

        val passwordInput = actions.ofType<Action.InputPassword>()
            .map { it.password }
            .doOnNext { password = it }
            .map { PasswordValidationStrategy(it) }
            .map { validateField(it) }
            .map { Change.Password(it) }

        val buttonState = Observable.combineLatest(
            emailInput,
            passwordInput,
            BiFunction { logIn: Change.Login, password: Change.Password ->
                return@BiFunction logIn.isFieldValid() && password.isFieldValid()
            })
            .map { Change.LogInButtonState(it) }

        val logIn = actions.ofType<Action.LogIn>()
            .debounce(500, TimeUnit.MILLISECONDS)
            .switchMap {
                logInUseCase.invoke(email, password)
                    .toObservable()
                    .map<Change> { Change.HideLoading }
                    .doOnNext { logInEventListener.onLogInClick() }
                    .onErrorReturn { Change.LogInError(it) }
                    .startWith(Change.ShowLoader)
            }

        disposables += Observable.merge(
            emailInput,
            passwordInput,
            buttonState,
            logIn
        ).scan(initialState, reducer)
            .distinctUntilChanged()
            .subscribe(state::postValue, Timber::e)
    }

    private fun validateField(validationStrategy: ValidationStrategy): FieldState {
        fieldValidator.setStrategy(validationStrategy)
        return fieldValidator.validate()
    }
}