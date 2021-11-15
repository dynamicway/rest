package me.study.rest.event

import me.study.rest.event.testdouble.SpyEventRepository
import org.junit.jupiter.api.BeforeEach

internal class EventServiceImplTest {

    private lateinit var eventRepository: SpyEventRepository
    private lateinit var eventService: EventService

    @BeforeEach
    internal fun setUp() {
        eventRepository = SpyEventRepository()
        eventService = EventServiceImpl(
            eventRepository
        )
    }

}