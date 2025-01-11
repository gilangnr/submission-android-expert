package com.example.eventexpert.core.domain.repository

import com.example.eventexpert.core.data.Resource
import com.example.eventexpert.core.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface IEventRepository {
    fun getAllEvent(): Flow<Resource<List<Event>>>

    fun getFavoriteEvent(): Flow<List<Event>>

    fun setFavoriteEvent(event: Event, state: Boolean)
}