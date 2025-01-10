package com.example.eventexpert.favorite

import androidx.lifecycle.ViewModel
import com.example.eventexpert.core.domain.usecase.EventUseCase

class FavoriteViewModel(eventUseCase: EventUseCase): ViewModel() {
    val favoriteEvent = eventUseCase.getFavoriteEvent()
}