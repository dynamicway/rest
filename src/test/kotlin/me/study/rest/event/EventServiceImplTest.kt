package me.study.rest.event

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.shouldBe
import me.study.rest.event.testdouble.SpyEventRepository
import org.junit.jupiter.api.BeforeEach

internal class EventServiceImplTest : ShouldSpec() {

    private lateinit var eventRepository: SpyEventRepository
    private lateinit var eventService: EventService

    override fun beforeEach(testCase: TestCase) {
        eventRepository = SpyEventRepository()
        eventService = EventServiceImpl(
            eventRepository
        )
    }

    @BeforeEach
    internal fun setUp() {
        eventRepository = SpyEventRepository()
        eventService = EventServiceImpl(
            eventRepository
        )
    }

    init {
        context("registerEvent") {
            should("EventRepository::save 실행") {
                val givenEvent = Event()
                eventRepository.saveReturns = givenEvent
                eventService.registerEvent(givenEvent)

                eventRepository.saveArguments shouldBe givenEvent
            }
        }
    }

}