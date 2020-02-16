package com.raiden.quickvloxchat.di

import com.raiden.chats.intent.ChatsEventListener
import com.raiden.login.intent.LogInEventListener
import com.raiden.quickvloxchat.navigation.callbacks.ChatsEventListenerImpl
import com.raiden.quickvloxchat.navigation.callbacks.LogInEventListenerImpl
import com.raiden.quickvloxchat.navigation.callbacks.SearchEventListenerImpl
import com.raiden.search.intent.SearchEventListener
import org.koin.dsl.module

val NAVIGATION_MODULE = module {
    factory<LogInEventListener> { LogInEventListenerImpl(get()) }
    factory<ChatsEventListener> { ChatsEventListenerImpl(get()) }
    factory<SearchEventListener> { SearchEventListenerImpl(get()) }
}