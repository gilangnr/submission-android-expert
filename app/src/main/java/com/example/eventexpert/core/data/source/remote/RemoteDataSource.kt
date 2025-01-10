package com.example.eventexpert.core.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.eventexpert.core.data.source.remote.network.ApiResponse
import com.example.eventexpert.core.data.source.remote.network.ApiService
import com.example.eventexpert.core.data.source.remote.response.EventResponse
import com.example.eventexpert.core.data.source.remote.response.ListEventsItem
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

    fun getAllEvents(): LiveData<ApiResponse<List<ListEventsItem>>> {
        val resultData = MutableLiveData<ApiResponse<List<ListEventsItem>>>()

        // Get data from remote API
        val client = apiService.getList()

        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                val dataArray = response.body()?.listEvents
                resultData.value = if (dataArray != null && dataArray.isNotEmpty()) {
                    ApiResponse.Success(dataArray)
                } else {
                    ApiResponse.Empty
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteDataSource", "Error: ${t.message}")
            }
        })

        return resultData
    }
}