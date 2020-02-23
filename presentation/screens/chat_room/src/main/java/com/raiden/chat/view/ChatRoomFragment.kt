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
import kotlinx.android.synthetic.main.message_form_input.*
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
        chat_send_message.setOnClickListener {
            val text = chat_input_message.text.toString()
            mviIntent.dispatch(Action.SendMessage(text))
        }
    }

    override fun renderState(state: State) {
        when (state) {
            is State.Messages -> chat_room_messages.swapItems(state.messages)
            is State.AddMessage -> chat_room_messages.addMessage(state.message)
        }
    }


    override fun initAction(): Action? = Action.LoadData
}