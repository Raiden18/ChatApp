package com.raiden.search.view.widgets.recycler

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raiden.core.utils.widgets.HorizontalRecyclerDividerItemDecoration
import com.raiden.search.R
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
        val dividerPadding = getResultDividerPadding()
        val itemDecorator = HorizontalRecyclerDividerItemDecoration(context, dividerPadding)
        addItemDecoration(itemDecorator)
    }

    fun updateUsers(users: List<UserViewModel>) {
        initAdapter()
        searchAdapter.items = users
    }

    fun hide() {
        initAdapter()
        searchAdapter.items = emptyList<UserViewModel>()
    }

    private fun initAdapter() {
        if (adapter == null) {
            adapter = searchAdapter
        }
    }

    private fun getResultDividerPadding(): Int {
        val nameLetterSize = resources.getDimension(R.dimen.name_letter_size)
        val recyclerItemPadding = resources.getDimension(R.dimen.recycler_item_padding)
        val recyclerItemNameMargin = resources.getDimension(R.dimen.recycler_item_margin_start)
        val resultPadding = nameLetterSize + recyclerItemPadding + recyclerItemNameMargin
        return resultPadding.toInt()
    }
}