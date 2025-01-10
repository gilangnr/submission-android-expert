package com.example.eventexpert.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event(
    val eventId: Int,
    val name: String,
    val summary: String,
    val description: String,
    val imageLogo: String,
    val mediaCover: String,
    val category: String,
    val ownerName: String,
    val cityName: String,
    val quota: Int,
    val registrants: Int,
    val beginTime: String,
    val endTime: String,
    val link: String,
    val isFavorite: Boolean
) : Parcelable