package com.example.bookkmm.android.di

import com.example.bookkmm.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val androidModule = module {
    viewModel { MainViewModel(get()) }
}