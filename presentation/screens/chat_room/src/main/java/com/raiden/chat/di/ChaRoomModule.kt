package com.raiden.chat.di

import com.raiden.chat.intent.ChatRoomMviIntent
import com.raiden.chat.view.ChatRoomFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val CHAT_ROOM_MODULE = module {
    scope(named<ChatRoomFragment>()) {
        viewModel {
            ChatRoomMviIntent(get(), get(), get())
        }
    }
}