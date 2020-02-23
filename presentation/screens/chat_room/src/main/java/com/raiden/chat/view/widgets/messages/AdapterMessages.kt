package com.raiden.chat.view.widgets.messages

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.raiden.chat.model.message.MessageViewModel

class AdapterMessages : AsyncListDifferDelegationAdapter<MessageViewModel>(MessagesDiffUtils()) {

    init {
        delegatesManager.addDelegate(senderAdapterDelegate())
            .addDelegate(addresseeAdapterDelegate())
    }
}