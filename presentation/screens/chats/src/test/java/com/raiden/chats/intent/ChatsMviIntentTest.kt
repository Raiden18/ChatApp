package com.raiden.chats.intent

import com.livetyping.beautyshop.core.testutils.BaseMviIntentTest
import com.raiden.chats.models.Action
import com.raiden.chats.models.State
import com.raiden.domain.models.Chat
import com.raiden.domain.usecases.chats.get.GetAllChatsUseCase
import io.mockk.*
import io.reactivex.Single
import io.reactivex.functions.Consumer
import org.junit.Before
import org.junit.Test

class ChatsMviIntentTest : BaseMviIntentTest() {

    private lateinit var chatsMviIntent: ChatsMviIntent
    private lateinit var getAllChatsUseCase: GetAllChatsUseCase
    private lateinit var consumer: Consumer<State>
    private lateinit var chatsEventListener: ChatsEventListener

    @Before
    fun setUp() {
        getAllChatsUseCase = spyk()
        consumer = spyk()
        chatsEventListener = spyk()
        chatsMviIntent = ChatsMviIntent(getAllChatsUseCase, chatsEventListener)
        chatsMviIntent.observableState.subscribe(consumer)
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
            consumer.accept(expectedLodingState)
            consumer.accept(expectedState)
        }
    }

    @Test
    fun `Should send onSearch event when that action was sent`() {
        //Given
        every {
            getAllChatsUseCase()
        } returns Single.just(emptyList())
        val expectedState = State(emptyList(), false, null)

        //When
        chatsMviIntent.dispatch(Action.OpenSearch)
        testSchedulerRule.triggerActions()

        //Then
        verifyOrder {
            consumer.accept(expectedState)
            chatsEventListener.onSearchClick()
        }
    }

    @Test
    fun `Should send onBack event when that action was sent`(){
        //Given
        every {
            getAllChatsUseCase()
        } returns Single.just(emptyList())
        val expectedState = State(emptyList(), false, null)

        //When
        chatsMviIntent.dispatch(Action.GoBack)
        testSchedulerRule.triggerActions()

        //Then
        verifyOrder {
            consumer.accept(expectedState)
            chatsEventListener.goBack()
        }
    }

}