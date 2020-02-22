package com.raiden.domain.usecases.chatroom.messages.send

import com.raiden.domain.models.Message
import com.raiden.domain.models.User
import com.raiden.domain.repositories.ChatRoomRepository
import com.raiden.domain.repositories.SessionRepository
import io.mockk.every
import io.mockk.spyk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class SendMessageImplTest {
    private lateinit var sendMessage: SendMessage
    private lateinit var chatRoomRepository: ChatRoomRepository
    private lateinit var sessionRepository: SessionRepository

    @Before
    fun setUp() {
        chatRoomRepository = spyk()
        sessionRepository = spyk()
        sendMessage = SendMessageImpl(chatRoomRepository, sessionRepository)
    }

    @Test
    fun `Should send message`() {
        //Given
        val text = "Message"
        val senderId = 1
        val receiverId = 2
        val userSender = User(senderId, "qwe@qwe.com", "qwe")
        val userReceiver = User(receiverId, "321@123.com", "321")
        val message = Message(text, senderId, receiverId)

        //When
        every {
            chatRoomRepository.getSelectedUserForChat()
        } returns Observable.just(userReceiver)
        every {
            sessionRepository.getUser()
        } returns Observable.just(userSender)

        sendMessage.invoke(text).blockingSubscribe({}, {})

        //Then
        verify(exactly = 1) {
            chatRoomRepository.sendMessage(message)
        }
    }
}