package com.raiden.domain.usecases.search

import com.raiden.domain.models.User
import com.raiden.domain.repositories.ChatGateway
import io.mockk.every
import io.mockk.spyk
import io.mockk.verifyOrder
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SearchUserByEmailUseCaseImplTest {
    private lateinit var searchUserByEmailUseCase: SearchUserByEmailUseCase
    private lateinit var chatGateway: ChatGateway

    @Before
    fun setUp() {
        chatGateway = spyk()
        searchUserByEmailUseCase = SearchUserByEmailUseCaseImpl(chatGateway)
    }

    @Test
    fun `Should load new page every time when use case was invoked`() {
        //Given
        val users = arrayListOf<User>()

        every {
            chatGateway.getUsers(any(), any())
        } returns Single.just(users)

        //When
        searchUserByEmailUseCase.invoke("qwe").blockingFirst()
        searchUserByEmailUseCase.invoke("qwer").blockingFirst()
        searchUserByEmailUseCase.invoke("qwe4").blockingFirst()


        //Then
        verifyOrder {
            chatGateway.getUsers(0, any())
            chatGateway.getUsers(1, any())
            chatGateway.getUsers(2, any())
        }
    }

    @Test
    fun `Shouldn't load new page if error occurs`() {
        //Given
        every {
            chatGateway.getUsers(any(), any())
        } returns Single.create<ArrayList<User>> { emitter ->
            emitter.onError(Throwable())
        }

        //When
        searchUserByEmailUseCase.invoke("qwe").subscribe({}, {})
        searchUserByEmailUseCase.invoke("qwe").subscribe({}, {})

        //Then
        verifyOrder {
            chatGateway.getUsers(0, any())
            chatGateway.getUsers(0, any())
        }
    }

    @Test
    fun `Should filter users by email`() {
        //Given
        val users = arrayListOf(
            User("emaiL1@gmail.com", ""),
            User("emaIl2@gmail.com ", ""),
            User("email3@gmail.com", ""),
            User(" 123234@gmail.com", "")
        )
        val emailRequest = " email"
        val expectedUserSize = 3
        every {
            chatGateway.getUsers(any(), any())
        } returns Single.just(users)

        //When
        val results = searchUserByEmailUseCase.invoke(emailRequest).blockingFirst()

        //Then
        assertEquals(expectedUserSize, results.size)
    }
}