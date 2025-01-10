package com.example.eventexpert.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.eventexpert.core.data.source.local.entity.EventEntity


@Dao
interface EventDao {

    @Query("SELECT * FROM event")
    fun getAllEvents(): LiveData<List<EventEntity>>

    @Query("SELECT * FROM event WHERE isFavorite = 1")
    fun getFavoriteEvents(): LiveData<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvents(events: List<EventEntity>)

    @Update
    fun updateFavoriteEvent(event: EventEntity)
}