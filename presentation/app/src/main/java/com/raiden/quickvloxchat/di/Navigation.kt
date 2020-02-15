package com.raiden.quickvloxchat.di

import com.raiden.login.intent.LogInEventListener
import com.raiden.login.view.LogInFragment
import com.raiden.quickvloxchat.navigation.callbacks.LogInEventListenerImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val NAVIGATION_MODULE = module {

    scope(named<LogInFragment>()) {
        scoped<LogInEventListener> { LogInEventListenerImpl(get()) }
    }
}