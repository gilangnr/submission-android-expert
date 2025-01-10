package com.example.eventexpert.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eventexpert.core.di.Injection
import com.example.eventexpert.core.domain.usecase.EventUseCase
import com.example.eventexpert.detail.DetailEventViewModel
import com.example.eventexpert.favorite.FavoriteViewModel
import com.example.eventexpert.home.HomeViewModel

class ViewModelFactory private constructor(private val eventUseCase: EventUseCase): ViewModelProvider.NewInstanceFactory(){

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideEventUseCase(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(eventUseCase) as T
            }
            modelClass.isAssignableFrom(DetailEventViewModel::class.java) -> {
                DetailEventViewModel(eventUseCase) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(eventUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

}