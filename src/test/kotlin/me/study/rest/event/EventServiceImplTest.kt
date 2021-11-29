package me.study.rest.event

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.shouldBe
import me.study.rest.event.testdouble.SpyEventRepository
import org.junit.jupiter.api.BeforeEach
import java.time.LocalDateTime

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
                val givenLocalDateTime = LocalDateTime.of(2021, 12, 25, 0, 0, 0)

                val givenRegisterEvent = RegisterEvent(
                    id = 0,
                    basePrice = 0,
                    maxPrice = 0,
                    limitOfEnrollment = 0,
                    offline = false,
                    free = false,
                    name = "",
                    beginEnrollmentDateTime = givenLocalDateTime,
                    closeEnrollmentDateTime = givenLocalDateTime,
                    beginEventDateTime = givenLocalDateTime,
                    endEventDateTime = givenLocalDateTime
                )
                val givenEvent = Event()
                eventRepository.saveReturns = givenEvent
                eventService.registerEvent(givenRegisterEvent)

                eventRepository.saveArguments shouldBe givenRegisterEvent.toEntity()
            }

            should("RegisterEvent 를 등록된 아이디를 포함하여 반환") {
                val givenLocalDateTime = LocalDateTime.of(2021, 12, 25, 0, 0, 0)

                val givenRegisterEvent = RegisterEvent(
                    id = 0,
                    basePrice = 0,
                    maxPrice = 0,
                    limitOfEnrollment = 0,
                    offline = false,
                    free = false,
                    name = "",
                    beginEnrollmentDateTime = givenLocalDateTime,
                    closeEnrollmentDateTime = givenLocalDateTime,
                    beginEventDateTime = givenLocalDateTime,
                    endEventDateTime = givenLocalDateTime
                )
                val givenEvent = Event(
                    id = 1L,
                    _basePrice = givenRegisterEvent.basePrice,
                    _maxPrice = givenRegisterEvent.maxPrice,
                    _limitOfEnrollment = givenRegisterEvent.limitOfEnrollment,
                    _offline = givenRegisterEvent.offline,
                    _free = givenRegisterEvent.free,
                    _name = givenRegisterEvent.name,
                    _beginEnrollmentDateTime = givenRegisterEvent.beginEnrollmentDateTime,
                    _closeEnrollmentDateTime = givenRegisterEvent.closeEnrollmentDateTime,
                    _beginEventDateTime = givenRegisterEvent.beginEventDateTime,
                    _endEventDateTime = givenRegisterEvent.endEventDateTime,
                )
                eventRepository.saveReturns = givenEvent
                val actualRegisterEvent = eventService.registerEvent(givenRegisterEvent)

                actualRegisterEvent.id shouldBe givenEvent.id
            }
        }
    }

}