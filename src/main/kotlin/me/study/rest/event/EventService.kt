package me.study.rest.event

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface EventService

@Service
@Transactional
class EventServiceImpl(
    private val eventRepository: EventRepository
): EventService {

}