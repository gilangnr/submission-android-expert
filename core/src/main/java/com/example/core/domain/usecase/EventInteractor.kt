package com.example.core.domain.usecase

import androidx.lifecycle.LiveData
import com.example.core.data.Resource
import com.example.core.domain.model.Event
import com.example.core.domain.repository.IEventRepository

class EventInteractor(private val eventRepository: IEventRepository): EventUseCase {
    override fun getAllEvent() = eventRepository.getAllEvent()

    override fun getFavoriteEvent() = eventRepository.getFavoriteEvent()

    override fun setFavoriteEvent(event: Event, state: Boolean) = eventRepository.setFavoriteEvent(event, state)

}