package com.example.eventexpert.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.EventUseCase

class HomeViewModel(eventUseCase: EventUseCase): ViewModel() {
    val event = eventUseCase.getAllEvent().asLiveData()
}