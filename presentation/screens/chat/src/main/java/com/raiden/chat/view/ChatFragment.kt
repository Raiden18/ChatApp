package com.raiden.chat.view

import android.os.Bundle
import android.view.View
import com.raiden.chat.R
import com.raiden.chat.intent.ChatMviIntent
import com.raiden.chat.model.Action
import com.raiden.chat.model.State
import com.raiden.core.mvi.CoreMviFragment
import com.raiden.core.mvi.CoreMviIntent
import kotlinx.android.synthetic.main.fragment_chat.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatFragment : CoreMviFragment<Action, State>(R.layout.fragment_chat) {
    override val mviIntent: CoreMviIntent<Action, State> by currentScope.viewModel<ChatMviIntent>(
        this
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chat_toolbar.title = "UserName"
    }

    override fun renderState(state: State) {

    }

    override fun initAction(): Action? = Action.LoadDialogHistory
}