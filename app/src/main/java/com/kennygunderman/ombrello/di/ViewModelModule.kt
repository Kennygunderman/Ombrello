package com.kennygunderman.ombrello.di

import com.kennygunderman.ombrello.ui.forecast.ForecastViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val vmModule = module {
    viewModel { ForecastViewModel(get()) }
}