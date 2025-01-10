package com.example.eventexpert.core.utils

import com.example.eventexpert.core.data.source.local.entity.EventEntity
import com.example.eventexpert.core.data.source.remote.response.ListEventsItem
import com.example.eventexpert.core.domain.model.Event

object DataMapper {
    fun mapResponsesToEntities(input: List<ListEventsItem>): List<EventEntity> {
        val eventList = ArrayList<EventEntity>()
        input.map {
            val event = EventEntity(
                id = it.id,
                name = it.name,
                summary = it.summary,
                description = it.description,
                imageLogo = it.imageLogo,
                mediaCover = it.mediaCover,
                category = it.category,
                ownerName = it.ownerName,
                cityName = it.cityName,
                quota = it.quota,
                registrants = it.registrants,
                beginTime = it.beginTime,
                endTime = it.endTime,
                link = it.link,
                isFavorite = false
            )
            eventList.add(event)
        }
        return eventList
    }

    fun mapEntitiesToDomain(input: List<EventEntity>): List<Event> =
        input.map {
            Event(
                eventId = it.id,
                name = it.name,
                summary = it.summary,
                description = it.description,
                imageLogo = it.imageLogo,
                mediaCover = it.mediaCover,
                category = it.category,
                ownerName = it.ownerName,
                cityName = it.cityName,
                quota = it.quota,
                registrants = it.registrants,
                beginTime = it.beginTime,
                endTime = it.endTime,
                link = it.link,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Event) = EventEntity(
        id = input.eventId,
        name = input.name,
        summary = input.summary,
        description = input.description,
        imageLogo = input.imageLogo,
        mediaCover = input.mediaCover,
        category = input.category,
        ownerName = input.ownerName,
        cityName = input.cityName,
        quota = input.quota,
        registrants = input.registrants,
        beginTime = input.beginTime,
        endTime = input.endTime,
        link = input.link,
        isFavorite = input.isFavorite
    )
}
