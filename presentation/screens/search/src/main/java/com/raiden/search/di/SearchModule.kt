package com.raiden.search.di

import com.raiden.search.intent.SearchMviIntent
import com.raiden.search.view.SearchFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val SEARCH_MODULE = module {
    scope(named<SearchFragment>()) {
        viewModel { SearchMviIntent(get(), get(), get()) }
    }
}