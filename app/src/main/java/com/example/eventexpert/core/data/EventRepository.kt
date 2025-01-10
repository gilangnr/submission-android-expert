package com.example.eventexpert.core.data

import android.provider.ContactsContract.Data
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.eventexpert.core.data.source.local.LocalDataSource
import com.example.eventexpert.core.data.source.remote.RemoteDataSource
import com.example.eventexpert.core.data.source.remote.network.ApiResponse
import com.example.eventexpert.core.data.source.remote.response.ListEventsItem
import com.example.eventexpert.core.domain.model.Event
import com.example.eventexpert.core.domain.repository.IEventRepository
import com.example.eventexpert.core.utils.AppExecutors
import com.example.eventexpert.core.utils.DataMapper

class EventRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IEventRepository{

    companion object {
        @Volatile
        private var instance: EventRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository(remoteData, localData, appExecutors)
            }
    }
    override fun getAllEvent(): LiveData<Resource<List<Event>>> =
        object : NetworkBoundResource<List<Event>, List<ListEventsItem>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<Event>> {
                return localDataSource.getAllEvents().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun createCall(): LiveData<ApiResponse<List<ListEventsItem>>> =
                remoteDataSource.getAllEvents()

            override fun saveCallResult(data: List<ListEventsItem>) {
                val eventList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertEvents(eventList)
            }

            override fun shouldFetch(data: List<Event>?): Boolean =
                data.isNullOrEmpty()

        }.asLiveData()

    override fun getFavoriteEvent(): LiveData<List<Event>> {
        return localDataSource.getFavoriteEvents().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteEvent(event: Event, state: Boolean) {
        val eventEntity = DataMapper.mapDomainToEntity(event)
        appExecutors.diskIO().execute { localDataSource.setFavoriteEvent(eventEntity, state)}
    }
}