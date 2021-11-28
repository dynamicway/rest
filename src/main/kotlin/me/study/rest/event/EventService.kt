package me.study.rest.event

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface EventService {
    fun registerEvent(registerEvent: Event): Event
}

@Service
@Transactional
class EventServiceImpl(
    private val eventRepository: EventRepository
) : EventService {
    override fun registerEvent(registerEvent: Event): Event {
        return eventRepository.save(registerEvent)
    }
}