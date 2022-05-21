package com.artjuna.artjuna_app.core.di

import com.artjuna.artjuna_app.ui.feeds.FeedsViewModel
import com.artjuna.artjuna_app.ui.home.HomeViewModel
import com.artjuna.artjuna_app.ui.search.SearchViewModel
import com.artjuna.artjuna_app.ui.store.StoreViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { FeedsViewModel(get()) }
    viewModel { StoreViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}
