package com.raiden.search.view.states

import com.raiden.search.view.SearchFragment
import com.raiden.search.view.widgets.emtymessage.animateHidingAndDo

internal class IdleState(
    fragment: SearchFragment
) : AbstractState(fragment) {

    override fun render() {
        loader.hide()
        emptyMessage.animateHidingAndDo(usersRecyclerView::hide)
    }
}