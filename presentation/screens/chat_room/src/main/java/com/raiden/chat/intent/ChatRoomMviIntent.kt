package com.raiden.chat.intent

import com.raiden.chat.model.Action
import com.raiden.chat.model.Change
import com.raiden.chat.model.State
import com.raiden.core.mvi.CoreMviIntent
import com.raiden.core.mvi.Reducer
import com.raiden.domain.usecases.chatroom.messages.GetMessagesHistoryUseCase
import com.raiden.domain.usecases.chatroom.user.get.GetSelectedUserForChat
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber

class ChatRoomMviIntent(
    private val getMessagesHistoryUseCase: GetMessagesHistoryUseCase,
    private val getSelectedUserForChat: GetSelectedUserForChat
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

        val loadDataAction = actions.ofType<Action.LoadData>()
            .flatMap { getSelectedUserForChat() }
            .flatMap { selectedUser ->
                getMessagesHistoryUseCase()
                    .map<Change> { Change.ShowMessages(it) }
                    .startWith(Change.ShowLoader)
            }

        disposables += loadDataAction
            .scan(initialState, reducer)
            .distinctUntilChanged()
            .subscribe(state::accept, Timber::e)
    }
}