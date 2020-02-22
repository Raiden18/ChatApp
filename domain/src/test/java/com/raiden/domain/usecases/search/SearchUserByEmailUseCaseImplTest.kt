package com.raiden.domain.usecases.search

import com.raiden.domain.models.User
import com.raiden.domain.repositories.ChatRepository
import io.mockk.every
import io.mockk.spyk
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SearchUserByEmailUseCaseImplTest {
    private lateinit var searchUserByEmailUseCase: SearchUserByEmailUseCase
    private lateinit var chatGateway: ChatRepository

    @Before
    fun setUp() {
        chatGateway = spyk()
        searchUserByEmailUseCase = SearchUserByEmailUseCaseImpl(chatGateway)
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
        val results = searchUserByEmailUseCase.invoke(emailRequest, 0).blockingFirst()

        //Then
        assertEquals(expectedUserSize, results.size)
    }

    @Test
    fun `Should return empty if request is empty`() {
        //Given
        val users = arrayListOf<User>(
            User("emaiL1@gmail.com", ""),
            User("emaIl2@gmail.com ", ""),
            User("email3@gmail.com", ""),
            User(" 123234@gmail.com", "")
        )
        val emailRequest = ""
        val expectedUserSize = 0
        every {
            chatGateway.getUsers(any(), any())
        } returns Single.just(users)

        //When
        val results = searchUserByEmailUseCase.invoke(emailRequest, 0).blockingFirst()

        //Then
        assertEquals(expectedUserSize, results.size)
    }
}