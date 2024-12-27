package com.xheghun.climatetrack.presentation.di

import com.xheghun.climatetrack.presentation.screens.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel {
        HomeViewModel(get(), get(), get(), get())
    }
}