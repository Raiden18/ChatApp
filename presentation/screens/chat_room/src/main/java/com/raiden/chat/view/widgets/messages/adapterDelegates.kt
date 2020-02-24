package com.raiden.chat.view.widgets.messages

import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.raiden.chat.R
import com.raiden.chat.model.message.MessageViewModel
import com.raiden.chat.model.message.ReceiverMessageViewModel
import com.raiden.chat.model.message.SenderMessageViewModel

inline fun <reified T : MessageViewModel> baseMessageAdapterDelegate(
    @LayoutRes layoutItem: Int,
    @IdRes textViewId: Int
) = adapterDelegateLayoutContainer<T, MessageViewModel>(layoutItem) {
    val textView = itemView.findViewById<TextView>(textViewId)


    bind {
        textView.text = item.getMessage().text
    }
}

fun senderAdapterDelegate() = baseMessageAdapterDelegate<SenderMessageViewModel>(
    R.layout.item_sender_message_text,
    R.id.item_sender_message_text
)

fun addresseeAdapterDelegate() = baseMessageAdapterDelegate<ReceiverMessageViewModel>(
    R.layout.item_receiver_message_text,
    R.id.item_receiver_message_text
)
