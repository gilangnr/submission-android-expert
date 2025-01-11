package com.example.eventexpert.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.EventUseCase

class FavoriteViewModel(eventUseCase: EventUseCase): ViewModel() {
    val favoriteEvent = eventUseCase.getFavoriteEvent().asLiveData()
}