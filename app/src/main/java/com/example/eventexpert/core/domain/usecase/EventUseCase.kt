package com.example.eventexpert.core.domain.usecase

import androidx.lifecycle.LiveData
import com.example.eventexpert.core.data.Resource
import com.example.eventexpert.core.domain.model.Event

interface EventUseCase {
    fun getAllEvent(): LiveData<Resource<List<Event>>>
    fun getFavoriteEvent(): LiveData<List<Event>>
    fun setFavoriteEvent(event: Event, state: Boolean)
}