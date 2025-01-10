package com.example.eventexpert.core.di

import android.content.Context
import com.example.eventexpert.core.data.EventRepository
import com.example.eventexpert.core.data.source.local.LocalDataSource
import com.example.eventexpert.core.data.source.local.room.EventDatabase
import com.example.eventexpert.core.data.source.remote.RemoteDataSource
import com.example.eventexpert.core.data.source.remote.network.ApiConfig
import com.example.eventexpert.core.domain.repository.IEventRepository
import com.example.eventexpert.core.domain.usecase.EventInteractor
import com.example.eventexpert.core.domain.usecase.EventUseCase
import com.example.eventexpert.core.utils.AppExecutors

object Injection {
    private fun provideRepository(context: Context): IEventRepository {
        val database = EventDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApiService())
        val localDataSource = LocalDataSource.getInstance(database.eventDao())
        val appExecutors = AppExecutors()

        return EventRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun provideEventUseCase(context: Context): EventUseCase {
        val repository = provideRepository(context)
        return EventInteractor(repository)
    }
}