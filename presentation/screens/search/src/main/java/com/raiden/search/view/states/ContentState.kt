package com.raiden.search.view.states

import com.raiden.search.models.UserViewModel
import com.raiden.search.view.SearchFragment
import com.raiden.search.view.widgets.emtymessage.animateHidingAndDo

internal class ContentState(
    fragment: SearchFragment,
    private val users: List<UserViewModel>
) : AbstractState(fragment) {

    override fun render() {
        if (emptyMessage.isHidden()) {
            renderContent()
        } else {
            animateHidingEmptyMessageAndThenRenderContent()
        }
    }

    private fun animateHidingEmptyMessageAndThenRenderContent() {
        emptyMessage.animateHidingAndDo(::renderContent)
    }

    private fun renderContent() {
        loader.hide()
        usersRecyclerView.updateUsers(users)
    }
}