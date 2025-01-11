package com.example.eventexpert.di

import com.example.core.domain.usecase.EventInteractor
import com.example.core.domain.usecase.EventUseCase
import com.example.eventexpert.detail.DetailEventViewModel
import com.example.eventexpert.favorite.FavoriteViewModel
import com.example.eventexpert.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<EventUseCase> { EventInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { DetailEventViewModel(get()) }
}