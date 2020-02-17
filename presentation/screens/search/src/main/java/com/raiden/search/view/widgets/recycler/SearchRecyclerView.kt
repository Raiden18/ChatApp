package com.raiden.search.view.widgets.recycler

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raiden.search.models.UserViewModel

internal class SearchRecyclerView(
    context: Context,
    attributeSet: AttributeSet
) : RecyclerView(context, attributeSet) {
    lateinit var onUserClick: (UserViewModel) -> Unit

    private val searchAdapter by lazy {
        SearchRecyclerAdapter(onUserClick)
    }

    init {
        layoutManager = LinearLayoutManager(context, VERTICAL, false)
    }

    fun updateUsers(users: List<UserViewModel>) {
        initAdapter()
        searchAdapter.items = users
    }

    private fun initAdapter() {
        if (adapter == null) {
            adapter = searchAdapter
        }
    }
}