package com.raiden.search.view.widgets.recycler

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.raiden.domain.models.User

internal class SearchRecyclerAdapter : AsyncListDifferDelegationAdapter<User>(SearchDiffUtils()) {

    init {
        delegatesManager.addDelegate(userAdapterDelegate())
    }
}