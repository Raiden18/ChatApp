package com.raiden.search.view.widgets.recycler

import androidx.recyclerview.widget.DiffUtil
import com.raiden.domain.models.User

internal class SearchDiffUtils : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.email == newItem.email
    }

    override fun getChangePayload(oldItem: User, newItem: User): Any? = Any()

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}