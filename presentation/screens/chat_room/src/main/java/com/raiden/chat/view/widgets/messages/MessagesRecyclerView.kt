package com.raiden.chat.view.widgets.messages

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raiden.chat.model.message.MessageViewModel

class MessagesRecyclerView(
    context: Context,
    attributeSet: AttributeSet
) : RecyclerView(context, attributeSet) {
    private val messageAdapter by lazy {
        AdapterMessages()
    }
    private val linearLayoutManager = LinearLayoutManager(context, VERTICAL, false).apply {
        stackFromEnd = true
    }

    init {
        layoutManager = linearLayoutManager
    }

    fun swapItems(items: List<MessageViewModel>) {
        initAdapter()
        messageAdapter.items = items
    }

    fun addMessage(messageViewModel: MessageViewModel) {
        initAdapter()
        val messages = ArrayList(messageAdapter.items)
        messages.add(messageViewModel)
        messageAdapter.items = messages
    }

    private fun initAdapter() {
        if (adapter == null) {
            adapter = messageAdapter
        }
    }
}