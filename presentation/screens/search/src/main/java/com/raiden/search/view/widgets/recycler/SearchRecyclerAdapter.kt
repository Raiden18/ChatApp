package com.raiden.search.view.widgets.recycler

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.raiden.domain.models.User
import com.raiden.search.models.UserViewModel

internal class SearchRecyclerAdapter(
    onUserClick: (UserViewModel) -> Unit
) : AsyncListDifferDelegationAdapter<UserViewModel>(SearchDiffUtils()) {

    init {
        delegatesManager.addDelegate(userAdapterDelegate(onUserClick))
    }
}