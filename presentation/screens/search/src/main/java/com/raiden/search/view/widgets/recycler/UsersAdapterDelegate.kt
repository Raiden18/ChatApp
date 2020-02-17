package com.raiden.search.view.widgets.recycler

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.raiden.core.extensions.setOnclickListener
import com.raiden.search.R
import com.raiden.search.models.UserViewModel
import kotlinx.android.synthetic.main.item_recycler_user.view.*

internal fun userAdapterDelegate(
    onUserClick: (UserViewModel) -> Unit
) = adapterDelegateLayoutContainer<UserViewModel, UserViewModel>(R.layout.item_recycler_user) {

    itemView.setOnclickListener {
        onUserClick(item)
    }

    bind {
        itemView.item_recycler_user_letter.text = item.firstLetter
        itemView.item_recycler_user_email.text = item.user.email
    }
}