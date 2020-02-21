package com.raiden.chat.view

import com.raiden.chat.R
import com.raiden.chat.intent.ChatMviIntent
import com.raiden.chat.model.Action
import com.raiden.chat.model.State
import com.raiden.core.mvi.CoreMviFragment
import com.raiden.core.mvi.CoreMviIntent
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatFragment : CoreMviFragment<Action, State>(R.layout.fragment_chat) {
    override val mviIntent: CoreMviIntent<Action, State> by currentScope.viewModel<ChatMviIntent>(
        this
    )

    override fun renderState(state: State) {

    }

    override fun initAction(): Action? = null
}