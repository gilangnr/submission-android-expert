package com.example.eventexpert.core.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.eventexpert.core.data.source.remote.network.ApiResponse
import com.example.eventexpert.core.data.source.remote.network.ApiService
import com.example.eventexpert.core.data.source.remote.response.EventResponse
import com.example.eventexpert.core.data.source.remote.response.ListEventsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val apiService: ApiService) {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service).also { instance = it }
            }
    }

    suspend fun getAllEvents(): Flow<ApiResponse<List<ListEventsItem>>> {
        return flow {
            try {
                val response = apiService.getList()
                val dataArray = response.listEvents

                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.listEvents))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}