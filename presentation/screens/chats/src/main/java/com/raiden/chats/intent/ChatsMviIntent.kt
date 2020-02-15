package com.raiden.chats.intent

import com.raiden.chats.models.Action
import com.raiden.chats.models.Change
import com.raiden.chats.models.State
import com.raiden.core.mvi.CoreMviIntent
import com.raiden.core.mvi.Reducer
import com.raiden.domain.usecases.chat.get.GetAllChatsUseCase
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber

class ChatsMviIntent(
    private val getAllChatsUseCase: GetAllChatsUseCase
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

        disposables += chats
            .scan(initialState, reducer)
            .distinctUntilChanged()
            .subscribe(state::setValue, Timber::e)
    }
}