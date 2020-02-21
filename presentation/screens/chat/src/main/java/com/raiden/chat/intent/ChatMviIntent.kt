package com.raiden.chat.intent

import com.raiden.chat.model.Action
import com.raiden.chat.model.Change
import com.raiden.chat.model.State
import com.raiden.core.mvi.CoreMviIntent
import com.raiden.core.mvi.Reducer
import com.raiden.domain.usecases.chat.history.GetMessagesHistoryUseCase
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber

class ChatMviIntent(
    private val getMessagesHistoryUseCase: GetMessagesHistoryUseCase
) : CoreMviIntent<Action, State>() {
    override val initialState: State = State.Idle

    private val reducer: Reducer<State, Change> = { state, change ->
        when (change) {
            is Change.ShowMessages -> State.Messages(change.messages)
            is Change.ShowLoader -> State.Loading
        }
    }

    init {
        bindActions()
    }

    override fun bindActions() {
        val dialogHistory = actions.ofType<Action.LoadDialogHistory>()
            .switchMap {
                getMessagesHistoryUseCase()
                    .map<Change> { Change.ShowMessages(it) }
                    .startWith(Change.ShowLoader)
            }

        disposables += dialogHistory
            .scan(initialState, reducer)
            .distinctUntilChanged()
            .subscribe(state::postValue, Timber::e)
    }
}