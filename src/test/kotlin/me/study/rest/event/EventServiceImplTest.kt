package me.study.rest.event

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import me.study.rest.event.testdouble.SpyEventRepository
import me.study.rest.util.ErrorField
import java.time.LocalDateTime

internal class EventServiceImplTest : ShouldSpec() {

    private lateinit var spyEventRepository: SpyEventRepository
    private lateinit var eventService: EventService

    override fun beforeEach(testCase: TestCase) {
        spyEventRepository = SpyEventRepository()
        eventService = EventServiceImpl(
            spyEventRepository
        )
    }

    init {
        should("EventRepository::save 실행") {
            val givenLocalDateTime = LocalDateTime.of(2021, 12, 25, 0, 0, 0)

            val givenRegisterEvent = RegisterEvent(
                id = 0,
                basePrice = 0,
                maxPrice = 0,
                limitOfEnrollment = 0,
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
                _eventStatus = givenRegisterEvent.eventStatus
            )

            spyEventRepository.saveReturns = givenEvent
            eventService.registerEvent(givenRegisterEvent)
            spyEventRepository.saveArguments shouldBe givenRegisterEvent.toEntity()
        }
        should("RegisterEvent 를 등록된 아이디를 포함하여 반환") {
            val givenLocalDateTime = LocalDateTime.of(2021, 12, 25, 0, 0, 0)

            val givenRegisterEvent = RegisterEvent(
                id = 0,
                basePrice = 0,
                maxPrice = 0,
                limitOfEnrollment = 0,
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
                _eventStatus = givenRegisterEvent.eventStatus
            )

            spyEventRepository.saveReturns = givenEvent
            val actualRegisterEvent = eventService.registerEvent(givenRegisterEvent)
            actualRegisterEvent.id shouldBe givenEvent.id
        }

        should("registerEvent 의 등록 제한이 존재하는데, 배팅 금액이 0원이 아닐 경우 RegisterEventBadRequestException 발생") {
            val givenLocalDateTime = LocalDateTime.of(2021, 12, 25, 0, 0, 0)

            val givenRegisterEvent = RegisterEvent(
                id = 0,
                basePrice = 100,
                maxPrice = 0,
                limitOfEnrollment = 100,
                name = "",
                beginEnrollmentDateTime = givenLocalDateTime,
                closeEnrollmentDateTime = givenLocalDateTime,
                beginEventDateTime = givenLocalDateTime,
                endEventDateTime = givenLocalDateTime
            )

            val actualException =
                shouldThrow<RegisterEventBadRequestException> { eventService.registerEvent(givenRegisterEvent) }
            actualException.message shouldBe "Base price higher than the highest price when there is a limit price"
            actualException.errorFields shouldContainExactly listOf(
                ErrorField("basePrice", givenRegisterEvent.basePrice),
                ErrorField("maxPrice", givenRegisterEvent.maxPrice),
                ErrorField("limitOfEnrollment", givenRegisterEvent.limitOfEnrollment)
            )

        }
    }

}