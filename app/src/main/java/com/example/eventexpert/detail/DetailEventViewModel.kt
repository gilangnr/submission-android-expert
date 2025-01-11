package com.example.eventexpert.detail

import androidx.lifecycle.ViewModel
import com.example.core.domain.model.Event
import com.example.core.domain.usecase.EventUseCase

class DetailEventViewModel(private val eventUseCase: EventUseCase): ViewModel() {
    fun setFavoriteEvent(event: Event, newStatus: Boolean) =
        eventUseCase.setFavoriteEvent(event, newStatus)
}