package com.raiden.search.view.widgets.recycler

import androidx.recyclerview.widget.DiffUtil
import com.raiden.search.models.UserViewModel

internal class SearchDiffUtils : DiffUtil.ItemCallback<UserViewModel>() {

    override fun areItemsTheSame(oldItem: UserViewModel, newItem: UserViewModel): Boolean {
        return oldItem.user.email == newItem.user.email
    }

    override fun getChangePayload(oldItem: UserViewModel, newItem: UserViewModel): Any? = Any()

    override fun areContentsTheSame(oldItem: UserViewModel, newItem: UserViewModel): Boolean {
        return oldItem == newItem
    }
}