package com.example.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.core.data.source.local.entity.EventEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface EventDao {

    @Query("SELECT * FROM event")
    fun getAllEvents(): Flow<List<EventEntity>>

    @Query("SELECT * FROM event WHERE isFavorite = 1")
    fun getFavoriteEvents(): Flow<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<EventEntity>)

    @Update
    fun updateFavoriteEvent(event: EventEntity)
}