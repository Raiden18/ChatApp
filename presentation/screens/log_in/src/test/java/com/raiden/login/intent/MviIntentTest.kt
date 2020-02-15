package com.raiden.login.intent

import androidx.lifecycle.Observer
import com.livetyping.beautyshop.core.testutils.BaseMviIntentTest
import com.raiden.login.models.Action
import com.raiden.login.models.State
import com.raiden.threestatestextinputlayout.FieldState
import io.mockk.spyk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.Before
import org.junit.Test

class MviIntentTest : BaseMviIntentTest() {
    private lateinit var mviIntent: MviIntent
    private lateinit var observer: Observer<State>

    @Before
    fun setUp() {
        mviIntent = MviIntent()
        observer = spyk()
        mviIntent.observableState.observeForever(observer)
    }

    @Test
    fun `Should return empty state for empty actions`() {
        //Given
        val emptyLoginAction = Action.InputLogIn("")
        val emptyPasswordAction = Action.InputLogIn("")
        val emptyState = State(
            logInState = FieldState.EmptyState,
            passwordState = FieldState.EmptyState,
            isButtonEnabled = false
        )

        //When
        mviIntent.dispatch(emptyLoginAction)
        mviIntent.dispatch(emptyPasswordAction)
        testSchedulerRule.triggerActions()

        //Then
        verify(exactly = 1) {
            observer.onChanged(emptyState)
        }
    }

    @Test
    fun `Should return invalid login state and invalid password states`() {
        //Given
        val emptyLoginAction = Action.InputLogIn("123")
        val passwordAction = Action.InputPassword("23")
        val state1 = State(
            logInState = FieldState.EmptyState,
            passwordState = FieldState.EmptyState,
            isButtonEnabled = false
        )
        val state2 = State(
            logInState = FieldState.InvalidState,
            passwordState = FieldState.InvalidState,
            isButtonEnabled = false
        )

        //When
        mviIntent.dispatch(emptyLoginAction)
        mviIntent.dispatch(passwordAction)
        testSchedulerRule.triggerActions()

        //Then
        verifyOrder {
            observer.onChanged(state1)
            observer.onChanged(state2)
        }
    }

    @Test
    fun `Should return valid login and valid password states`() {
        //Given
        val emptyLoginAction = Action.InputLogIn("123123123")
        val passwordAction = Action.InputPassword("123123123123")
        val state1 = State(
            logInState = FieldState.EmptyState,
            passwordState = FieldState.EmptyState,
            isButtonEnabled = false
        )
        val state2 = State(
            logInState = FieldState.ValidState,
            passwordState = FieldState.ValidState,
            isButtonEnabled = true
        )

        //When
        mviIntent.dispatch(emptyLoginAction)
        mviIntent.dispatch(passwordAction)
        testSchedulerRule.triggerActions()

        //Then
        verifyOrder {
            observer.onChanged(state1)
            observer.onChanged(state2)
        }
    }

}