package com.raiden.chats.view

import android.view.View
import com.raiden.chats.R
import com.raiden.chats.intent.ChatsMviIntent
import com.raiden.chats.models.Action
import com.raiden.chats.models.State
import com.raiden.core.mvi.CoreMviFragment
import com.raiden.core.mvi.CoreMviIntent
import kotlinx.android.synthetic.main.fragment_chats.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatsFragment : CoreMviFragment<Action, State>(R.layout.fragment_chats) {

    override val mviIntent: CoreMviIntent<Action, State> by currentScope.viewModel<ChatsMviIntent>(
        this
    )

    override fun renderState(state: State) {
        with(state) {
            when {
                chats.isEmpty() -> renderEmptyState()
                isShowLoading -> renderLoading()
            }
        }
    }

    override fun initAction(): Action = Action.LoadChats

    private fun renderEmptyState() {
        chats_empty_message_lentach.visibility = View.VISIBLE
        chats_loader.visibleChildId = R.id.chats_content_container
    }

    private fun renderLoading() {
        chats_loader.showProgress()
    }
}