package com.raiden.chats.intent

import com.raiden.chats.models.Action
import com.raiden.chats.models.Change
import com.raiden.chats.models.State
import com.raiden.core.mvi.CoreMviIntent
import com.raiden.core.mvi.Reducer
import com.raiden.domain.usecases.chats.get.GetAllChatsUseCase
import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber

class ChatsMviIntent(
    private val getAllChatsUseCase: GetAllChatsUseCase,
    private val chatsEventListener: ChatsEventListener
) : CoreMviIntent<Action, State>() {
    override val initialState: State = State()

    private val reducer: Reducer<State, Change> = { state, change ->
        when (change) {
            is Change.EmptyChats -> state.copy(
                chats = emptyList(),
                isShowLoading = false,
                error = null
            )
            is Change.ShowChats -> state.copy(
                chats = change.chats,
                isShowLoading = false,
                error = null
            )
            is Change.ShowLoading -> state.copy(
                isShowLoading = true
            )
            is Change.Idle -> state.copy(
                chats = state.chats,
                isShowLoading = false,
                error = null
            )
        }
    }

    init {
        bindActions()
    }

    override fun bindActions() {
        val chats = actions.ofType<Action.LoadChats>()
            .switchMap {
                getAllChatsUseCase()
                    .toObservable()
                    .map<Change> { Change.ShowChats(it) }
                    .defaultIfEmpty(Change.EmptyChats)
                    .startWith(Change.ShowLoading)
            }

        val onSearchClick = actions.ofType<Action.OpenSearch>()
            .map<Change> { Change.Idle }
            .doOnNext { chatsEventListener.onSearchClick() }

        val goBackClick = actions.ofType<Action.GoBack>()
            .map<Change> { Change.Idle }
            .doOnNext { chatsEventListener.goBack() }

        disposables += Observable.merge(chats, onSearchClick, goBackClick)
            .scan(initialState, reducer)
            .distinctUntilChanged()
            .subscribe(state::accept, Timber::e)
    }
}