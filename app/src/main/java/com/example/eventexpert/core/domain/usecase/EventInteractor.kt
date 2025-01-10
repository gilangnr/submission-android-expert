package com.example.eventexpert.core.domain.usecase

import androidx.lifecycle.LiveData
import com.example.eventexpert.core.data.Resource
import com.example.eventexpert.core.domain.model.Event
import com.example.eventexpert.core.domain.repository.IEventRepository

class EventInteractor(private val eventRepository: IEventRepository): EventUseCase {
    override fun getAllEvent() = eventRepository.getAllEvent()

    override fun getFavoriteEvent() = eventRepository.getFavoriteEvent()

    override fun setFavoriteEvent(event: Event, state: Boolean) = eventRepository.setFavoriteEvent(event, state)

}