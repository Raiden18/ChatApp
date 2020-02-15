package com.raiden.chats.intent

import androidx.lifecycle.Observer
import com.livetyping.beautyshop.core.testutils.BaseMviIntentTest
import com.raiden.chats.models.Action
import com.raiden.chats.models.State
import com.raiden.domain.models.Chat
import com.raiden.domain.usecases.chat.get.GetAllChatsUseCase
import io.mockk.every
import io.mockk.spyk
import io.mockk.verifyOrder
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class ChatsMviIntentTest : BaseMviIntentTest() {

    private lateinit var chatsMviIntent: ChatsMviIntent
    private lateinit var getAllChatsUseCase: GetAllChatsUseCase
    private lateinit var observer: Observer<State>

    @Before
    fun setUp() {
        getAllChatsUseCase = spyk()
        observer = spyk()
        chatsMviIntent = ChatsMviIntent(getAllChatsUseCase)
        chatsMviIntent.observableState.observeForever(observer)
    }

    @Test
    fun `Should return empty state if there are no chats`() {
        //Given
        val chats = listOf<Chat>()
        every {
            getAllChatsUseCase()
        } returns Single.just(chats)
        val expectedState = State(
            chats = emptyList(),
            isShowLoading = false,
            error = null
        )
        val expectedLodingState = State(
            chats = emptyList(),
            isShowLoading = true,
            error = null
        )

        //When
        chatsMviIntent.dispatch(Action.LoadChats)
        testSchedulerRule.triggerActions()

        //Then
        verifyOrder {
            observer.onChanged(expectedLodingState)
            observer.onChanged(expectedState)
        }
    }
}