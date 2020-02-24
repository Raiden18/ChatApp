package com.raiden.chat.view.widgets.messages

import androidx.recyclerview.widget.DiffUtil
import com.raiden.chat.model.message.MessageViewModel

class MessagesDiffUtils : DiffUtil.ItemCallback<MessageViewModel>() {
    override fun areItemsTheSame(oldItem: MessageViewModel, newItem: MessageViewModel): Boolean {
        return oldItem.getMessage().text == newItem.getMessage().text
    }

    override fun getChangePayload(oldItem: MessageViewModel, newItem: MessageViewModel): Any? =
        Any()

    override fun areContentsTheSame(oldItem: MessageViewModel, newItem: MessageViewModel): Boolean {
        return oldItem.getMessage() == newItem.getMessage()
    }
}