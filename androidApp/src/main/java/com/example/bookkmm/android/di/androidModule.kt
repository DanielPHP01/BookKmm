package com.example.bookkmm.android.di

import com.example.bookkmm.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidModule = module {
    viewModel { MainViewModel(get(), get()) }
    factory<CoroutineScope> { CoroutineScope(Dispatchers.Main) } // Измените `scoped` на `factory`
}