package com.raiden.chat.view

import android.os.Bundle
import android.view.View
import com.raiden.chat.R
import com.raiden.chat.intent.ChatRoomMviIntent
import com.raiden.chat.model.Action
import com.raiden.chat.model.State
import com.raiden.core.mvi.CoreMviFragment
import com.raiden.core.mvi.CoreMviIntent
import kotlinx.android.synthetic.main.fragment_chat.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatRoomFragment @Deprecated("") constructor() :
    CoreMviFragment<Action, State>(R.layout.fragment_chat) {
    companion object {
        private const val TITLE = "TITLE"
        @Suppress("DEPRECATION")
        fun newInstance(userName: String) = ChatRoomFragment().apply {
            arguments = Bundle().apply {
                putString(TITLE, userName)
            }
        }
    }

    override val mviIntent: CoreMviIntent<Action, State> by currentScope.viewModel<ChatRoomMviIntent>(
        this
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chat_room_toolbar.title = arguments!!.getString(TITLE)
    }

    override fun renderState(state: State) {

    }

    override fun initAction(): Action? = Action.LoadData
}