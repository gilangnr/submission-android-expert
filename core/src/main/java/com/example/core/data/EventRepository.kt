package com.example.core.data

import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.response.ListEventsItem
import com.example.core.domain.model.Event
import com.example.core.domain.repository.IEventRepository
import com.example.core.utils.AppExecutors
import com.example.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EventRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IEventRepository {

    override fun getAllEvent(): Flow<Resource<List<Event>>> =
        object : NetworkBoundResource<List<Event>, List<ListEventsItem>>() {
            override fun loadFromDB(): Flow<List<Event>> {
                return localDataSource.getAllEvents().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ListEventsItem>>> =
                remoteDataSource.getAllEvents()

            override suspend fun saveCallResult(data: List<ListEventsItem>) {
                val eventList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertEvents(eventList)
            }

            override fun shouldFetch(data: List<Event>?): Boolean =
                data.isNullOrEmpty()

        }.asFlow()

    override fun getFavoriteEvent(): Flow<List<Event>> {
        return localDataSource.getFavoriteEvents().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteEvent(event: Event, state: Boolean) {
        val eventEntity = DataMapper.mapDomainToEntity(event)
        appExecutors.diskIO().execute { localDataSource.setFavoriteEvent(eventEntity, state)}
    }
}