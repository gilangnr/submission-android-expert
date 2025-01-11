package com.example.eventexpert.core.data.source.local

import androidx.lifecycle.LiveData
import com.example.eventexpert.core.data.source.local.entity.EventEntity
import com.example.eventexpert.core.data.source.local.room.EventDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource private constructor(private val eventDao: EventDao){
    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(eventDao: EventDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(eventDao).also { instance = it }
            }
    }

    fun getAllEvents(): Flow<List<EventEntity>> = eventDao.getAllEvents()

    fun getFavoriteEvents(): Flow<List<EventEntity>> = eventDao.getFavoriteEvents()

    suspend fun insertEvents(eventList: List<EventEntity>) = eventDao.insertEvents(eventList)

    fun setFavoriteEvent(event: EventEntity, newState: Boolean) {
        event.isFavorite = newState
        eventDao.updateFavoriteEvent(event)
    }
}