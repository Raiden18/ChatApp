package com.raiden.login.di

import com.raiden.core.widgets.dialogs.ShowErrorDialog
import com.raiden.core.widgets.dialogs.ShowErrorDialogImpl
import com.raiden.login.intent.MviIntent
import com.raiden.login.view.LogInFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val LOG_IN_MODULE = module {
    scope(named<LogInFragment>()) {
        viewModel { MviIntent(get()) }

        scoped<ShowErrorDialog> { ShowErrorDialogImpl() }
    }
}