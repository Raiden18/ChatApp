package com.raiden.chat.intent

import com.livetyping.beautyshop.core.testutils.BaseMviIntentTest
import com.raiden.chat.model.Action
import com.raiden.chat.model.State
import com.raiden.chat.model.message.MessageViewModel
import com.raiden.domain.models.Message
import com.raiden.domain.models.User
import com.raiden.domain.usecases.chatroom.messages.get.GetMessagesHistory
import com.raiden.domain.usecases.chatroom.messages.send.SendMessage
import com.raiden.domain.usecases.chatroom.user.get.GetSelectedUserForChat
import io.mockk.*
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import org.junit.Before
import org.junit.Test

class ChatRoomMviIntentTest : BaseMviIntentTest() {

    private lateinit var chatMviIntent: ChatRoomMviIntent
    private lateinit var getMessagesHistoryUseCase: GetMessagesHistory
    private lateinit var getUserForChatUseCase: GetSelectedUserForChat
    private lateinit var sendMessage: SendMessage
    private lateinit var consumer: Consumer<State>
    private lateinit var messageViewModelMapper: MessageViewModelMapper

    @Before
    fun setUp() {
        getMessagesHistoryUseCase = spyk()
        consumer = spyk()
        getUserForChatUseCase = spyk()
        sendMessage = spyk()
        messageViewModelMapper = spyk()
        chatMviIntent = ChatRoomMviIntent(
            getMessagesHistoryUseCase,
            getUserForChatUseCase,
            sendMessage,
            messageViewModelMapper
        )
        chatMviIntent.observableState.subscribe(consumer)
    }

    @Test
    fun `Should load chat room data  when that action was sent`() {
        //Given
        val messages = listOf<Message>(mockk())
        val user: User = mockk(relaxed = true)
        val messageViewModel: MessageViewModel = mockk()
        val messagesViewModel = listOf<MessageViewModel>(messageViewModel)
        every {
            getMessagesHistoryUseCase()
        } returns Observable.just(messages)
        every {
            getUserForChatUseCase.invoke()
        } returns Observable.just(user)
        every {
            messageViewModelMapper.map(any(), any())
        } returns messageViewModel
        //When
        chatMviIntent.dispatch(Action.LoadData)
        testSchedulerRule.triggerActions()

        //Then
        verifyOrder {
            consumer.accept(State.Loading)
            consumer.accept(State.Messages(messagesViewModel))
        }
    }

    @Test
    fun `Should send message`() {
        //Given
        val message = "Message"
        val action = Action.SendMessage(message)

        //When
        chatMviIntent.dispatch(action)
        testSchedulerRule.triggerActions()

        //Then
        verify {
            sendMessage(message)
            consumer.accept(State.Idle)
        }

    }

}