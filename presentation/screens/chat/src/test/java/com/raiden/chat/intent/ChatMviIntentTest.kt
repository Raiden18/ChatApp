package com.raiden.chat.intent

import androidx.lifecycle.Observer
import com.livetyping.beautyshop.core.testutils.BaseMviIntentTest
import com.raiden.chat.model.Action
import com.raiden.chat.model.State
import com.raiden.domain.models.Message
import com.raiden.domain.usecases.chat.history.GetMessagesHistoryUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verifyOrder
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class ChatMviIntentTest : BaseMviIntentTest() {

    private lateinit var chatMviIntent: ChatMviIntent
    private lateinit var getMessagesHistoryUseCase: GetMessagesHistoryUseCase
    private lateinit var observer: Observer<State>
    @Before
    fun setUp() {
        getMessagesHistoryUseCase = spyk()
        observer = spyk()
        chatMviIntent = ChatMviIntent(getMessagesHistoryUseCase)
        chatMviIntent.observableState.observeForever(observer)
    }

    @Test
    fun `Should load messages when that action was sent`() {
        //Given
        val messages = listOf<Message>(mockk())
        every {
            getMessagesHistoryUseCase()
        } returns Observable.just(messages)


        //When
        chatMviIntent.dispatch(Action.LoadDialogHistory)
        testSchedulerRule.triggerActions()

        //Then
        verifyOrder {
            observer.onChanged(State.Loading)
            observer.onChanged(State.Messages(messages))
        }
    }
}