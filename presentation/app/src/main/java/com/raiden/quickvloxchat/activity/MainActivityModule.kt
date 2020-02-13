package com.raiden.quickvloxchat.activity

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val MAIN_ACTIVITY_MODULE = module {
    scope(named<MainActivity>()) {
        viewModel { ActivityViewModel(get()) }
    }
}