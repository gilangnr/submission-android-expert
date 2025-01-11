package com.example.core.data.source.local

import com.example.core.data.source.local.entity.EventEntity
import com.example.core.data.source.local.room.EventDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val eventDao: EventDao){

    fun getAllEvents(): Flow<List<EventEntity>> = eventDao.getAllEvents()

    fun getFavoriteEvents(): Flow<List<EventEntity>> = eventDao.getFavoriteEvents()

    suspend fun insertEvents(eventList: List<EventEntity>) = eventDao.insertEvents(eventList)

    fun setFavoriteEvent(event: EventEntity, newState: Boolean) {
        event.isFavorite = newState
        eventDao.updateFavoriteEvent(event)
    }
}