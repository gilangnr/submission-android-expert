package com.example.core.data.source.remote.network

import com.example.core.data.source.remote.response.EventResponse
import retrofit2.http.GET

interface ApiService {
    @GET("events")
    suspend fun getList(): EventResponse
}