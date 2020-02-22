package com.raiden.chat.intent

import com.raiden.chat.model.Action
import com.raiden.chat.model.Change
import com.raiden.chat.model.State
import com.raiden.core.mvi.CoreMviIntent
import com.raiden.core.mvi.Reducer
import com.raiden.domain.usecases.chatroom.messages.get.GetMessagesHistory
import com.raiden.domain.usecases.chatroom.messages.send.SendMessage
import com.raiden.domain.usecases.chatroom.user.get.GetSelectedUserForChat
import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber

class ChatRoomMviIntent(
    private val getMessagesHistoryUseCase: GetMessagesHistory,
    private val getSelectedUserForChat: GetSelectedUserForChat,
    private val sendMessage: SendMessage
) : CoreMviIntent<Action, State>() {
    override val initialState: State = State.Idle

    private val reducer: Reducer<State, Change> = { state, change ->
        when (change) {
            is Change.ShowMessages -> State.Messages(change.messages)
            is Change.ShowLoader -> State.Loading
            is Change.DoNothing -> state
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

        val sendMessage = actions.ofType<Action.SendMessage>()
            .map { it.text }
            .flatMap {
                sendMessage(it)
                    .map<Change> { Change.DoNothing }
            }


        disposables += Observable.merge(loadDataAction, sendMessage)
            .scan(initialState, reducer)
            .distinctUntilChanged()
            .subscribe(state::accept, Timber::e)
    }
}