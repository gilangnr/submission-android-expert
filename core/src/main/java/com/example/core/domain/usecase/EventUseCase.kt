package com.example.core.domain.usecase

import com.example.core.data.Resource
import com.example.core.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface EventUseCase {
    fun getAllEvent(): Flow<Resource<List<Event>>>
    fun getFavoriteEvent(): Flow<List<Event>>
    fun setFavoriteEvent(event: Event, state: Boolean)
}