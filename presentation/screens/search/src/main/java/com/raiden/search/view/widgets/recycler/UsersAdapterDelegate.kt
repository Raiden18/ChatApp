package com.raiden.search.view.widgets.recycler

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.raiden.domain.models.User
import com.raiden.search.R
import kotlinx.android.synthetic.main.item_recycler_user.view.*

internal fun userAdapterDelegate(
) = adapterDelegateLayoutContainer<User, User>(R.layout.item_recycler_user) {

    bind {
        val user = item
        val emailFirstLetter = user.email[0].toString()
        itemView.item_recycler_user_letter.text = emailFirstLetter
        itemView.item_recycler_user_email.text = user.email
    }
}