package com.raiden.search.view.widgets.recycler

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raiden.domain.models.User

internal class SearchRecyclerView(
    context: Context,
    attributeSet: AttributeSet
) : RecyclerView(context, attributeSet) {

    private val searchAdapter by lazy {
        SearchRecyclerAdapter()
    }

    init {
        layoutManager = LinearLayoutManager(context, VERTICAL, false)
    }

    fun updateUsers(users: List<User>) {
        initAdapter()
        searchAdapter.items = users
    }

    private fun initAdapter() {
        if (adapter == null) {
            adapter = searchAdapter
        }
    }
}