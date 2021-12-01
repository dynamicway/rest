package me.study.rest.event

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface EventService {
    fun registerEvent(registerEvent: RegisterEvent): RegisterEvent
}

@Service
@Transactional
class EventServiceImpl(
    private val eventRepository: EventRepository
) : EventService {
    override fun registerEvent(registerEvent: RegisterEvent): RegisterEvent {
        registerEvent.validate()
        val eventEntity = eventRepository.save(registerEvent.toEntity())
        return RegisterEvent.of(eventEntity)
    }
}
