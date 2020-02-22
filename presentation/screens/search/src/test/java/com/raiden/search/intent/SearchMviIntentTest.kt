package com.raiden.search.intent

import com.livetyping.beautyshop.core.testutils.BaseMviIntentTest
import com.raiden.core.utils.rx.schedule.SchedulerProvider
import com.raiden.domain.models.User
import com.raiden.domain.usecases.chatroom.user.select.SelectUserForChat
import com.raiden.domain.usecases.search.SearchUserByEmailUseCase
import com.raiden.search.models.Action
import com.raiden.search.models.State
import com.raiden.search.models.UserViewModel
import io.mockk.*
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class SearchMviIntentTest : BaseMviIntentTest() {

    private lateinit var searchEventListener: SearchEventListener
    private lateinit var searchMviIntent: SearchMviIntent
    private lateinit var consumer: Consumer<State>
    private lateinit var searchUserByEmailUseCase: SearchUserByEmailUseCase
    private lateinit var schedulerProvider: SchedulerProvider
    private lateinit var testScheduler: TestScheduler
    private lateinit var userViewModelConverter: UserViewModelConverter
    private lateinit var selectUserForChatUseCase: SelectUserForChat

    @Before
    fun setUp() {
        searchEventListener = spyk()
        consumer = spyk()
        searchUserByEmailUseCase = spyk()
        schedulerProvider = spyk()
        userViewModelConverter = spyk()
        selectUserForChatUseCase = spyk()
        testScheduler = TestScheduler()
        every {
            schedulerProvider.io()
        } returns testScheduler
        searchMviIntent =
            SearchMviIntent(
                searchEventListener,
                searchUserByEmailUseCase,
                selectUserForChatUseCase,
                schedulerProvider,
                userViewModelConverter
            )
        searchMviIntent.observableState.subscribe(consumer)
    }

    @Test
    fun `Should goBack when that action was sent`() {
        //Given
        val expectedState = State.Idle

        //When
        searchMviIntent.dispatch(Action.GoBack)
        testSchedulerRule.triggerActions()

        //Then
        verifyOrder {
            consumer.accept(expectedState)
            searchEventListener.onBackClick()
        }
    }

    @Test
    fun `Should show empty state if there is empty list of users`() {
        //Given
        val searchRequest = "123123@asdasd.com"
        val expectedEmptyState = State.EmptyState
        val loaderState = State.LoaderState
        every {
            searchUserByEmailUseCase(any(), any())
        } returns Observable.just(arrayListOf())

        //When
        searchMviIntent.dispatch(Action.Search(searchRequest))
        testSchedulerRule.triggerActions()
        testScheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)

        //Then
        verifyOrder {
            consumer.accept(loaderState)
            consumer.accept(expectedEmptyState)
        }
    }

    @Test
    fun `Should show content if there is list of user is not empty`() {
        //Given
        val searchRequest = "123123@asdasd.com"
        val viewModelUser: UserViewModel = mockk()
        val user: User = mockk()
        val contentState = State.ContentState(listOf(viewModelUser))
        val loaderState = State.LoaderState
        every {
            searchUserByEmailUseCase(any(), any())
        } returns Observable.just(arrayListOf(user))
        every {
            userViewModelConverter.convert(any())
        } returns viewModelUser

        //When
        searchMviIntent.dispatch(Action.Search(searchRequest))
        testSchedulerRule.triggerActions()
        testScheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)

        //Then
        verifyOrder {
            consumer.accept(loaderState)
            consumer.accept(contentState)
        }
    }

    @Test
    fun `Should show idle state if request is empty`() {
        //Given
        val emptySearchRequest = ""
        val users = arrayListOf<User>(mockk())
        val userViewModel: UserViewModel = mockk()
        every {
            searchUserByEmailUseCase(any(), any())
        } returns Observable.just(users)
        every {
            userViewModelConverter.convert(any())
        } returns userViewModel

        //When
        searchMviIntent.dispatch(Action.Search("asdasd"))
        searchMviIntent.dispatch(Action.Search(emptySearchRequest))
        testSchedulerRule.triggerActions()
        testScheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)

        //Then
        verifyOrder {
            consumer.accept(State.Idle)
        }
    }

    @Test
    fun `Should selectUser for chat and then send onUserClick event when that user was select`() {
        //Given
        val user: User = mockk(relaxed = true)
        val userViewModel = UserViewModel("1", user)
        val action = Action.SelectUser(userViewModel)
        every {
            selectUserForChatUseCase.invoke(any())
        } returns Observable.empty()

        //When
        searchMviIntent.dispatch(action)
        testSchedulerRule.triggerActions()

        //Then
        verify {
            selectUserForChatUseCase.invoke(user)
            searchEventListener.onUserClick(user.fullName)
        }
    }

    @Test
    fun `Should load users once only if the same email was dispatched twice`() {
        //Given
        val request = "request"
        val action1 = Action.Search(request)
        val action2 = Action.Search(request)
        val users = arrayListOf<User>(mockk())
        val userViewModel: UserViewModel = mockk()
        every {
            searchUserByEmailUseCase(any(), any())
        } returns Observable.just(users)
        every {
            userViewModelConverter.convert(any())
        } returns userViewModel

        //When
        searchMviIntent.dispatch(action1)
        testScheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)
        searchMviIntent.dispatch(action2)
        testScheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)
        testSchedulerRule.triggerActions()

        //Then
        verify(exactly = 1) {
            searchUserByEmailUseCase(request, any())
        }
    }

    @Test
    fun `Should load users twice if different requests were dispatched twice`() {
        //Given
        val request1 = "request1"
        val request2 = "request2"
        val action1 = Action.Search(request1)
        val action2 = Action.Search(request2)
        val users = arrayListOf<User>(mockk())
        val userViewModel: UserViewModel = mockk()
        every {
            searchUserByEmailUseCase(any(), any())
        } returns Observable.just(users)
        every {
            userViewModelConverter.convert(any())
        } returns userViewModel

        //When
        searchMviIntent.dispatch(action1)
        testScheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)
        searchMviIntent.dispatch(action2)
        testScheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)
        testSchedulerRule.triggerActions()
        //Then
        verifyOrder {
            searchUserByEmailUseCase(request1, any())
            searchUserByEmailUseCase(request2, any())
        }
    }
}