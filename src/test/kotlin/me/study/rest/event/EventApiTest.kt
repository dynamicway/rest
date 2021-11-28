package me.study.rest.event

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.shouldBe
import org.springframework.hateoas.MediaTypes
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDateTime

internal class EventApiTest : ShouldSpec() {
    private lateinit var eventApi: EventApi
    private lateinit var spyEventService: SpyEventService
    private val mockMvc: MockMvc by lazy { MockMvcBuilders.standaloneSetup(eventApi).build() }
    private val objectMapper: ObjectMapper by lazy {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }

    override fun beforeTest(testCase: TestCase) {
        spyEventService = SpyEventService()
        eventApi = EventApi(
            spyEventService
        )
    }

    init {
        context("registerEvent") {
            should("응답 검증") {
                val givenRequest = RegisterEventDto(
                    basePrice = 0,
                    maxPrice = 0,
                    limitOfEnrollment = 0,
                    offline = false,
                    free = false,
                    name = "",
                    beginEnrollmentDateTime = LocalDateTime.of(2021, 1, 1, 1, 1),
                    closeEnrollmentDateTime = LocalDateTime.of(2021, 1, 1, 1, 1, 1),
                    beginEventDateTime = LocalDateTime.of(2021, 1, 1, 1, 1, 1),
                    endEventDateTime = LocalDateTime.of(2021, 1, 1, 1, 1, 1)
                )
                spyEventService.registerEventReturns = givenRequest.toEntity()
                mockMvc.perform(
                    post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(givenRequest))
                )
                    .andExpect(status().isCreated)
                    .andExpect(header().exists(HttpHeaders.LOCATION))
                    .andExpect(jsonPath("id").exists())
            }
            should("행위 검증") {
                val givenRequest = RegisterEventDto(
                    basePrice = 0,
                    maxPrice = 0,
                    limitOfEnrollment = 0,
                    offline = false,
                    free = false,
                    name = "",
                    beginEnrollmentDateTime = LocalDateTime.of(2021, 1, 1, 1, 1),
                    closeEnrollmentDateTime = LocalDateTime.of(2021, 1, 1, 1, 1, 1),
                    beginEventDateTime = LocalDateTime.of(2021, 1, 1, 1, 1, 1),
                    endEventDateTime = LocalDateTime.of(2021, 1, 1, 1, 1, 1)
                )
                val givenEvent = Event(
                    id = 0,
                    _basePrice = givenRequest.basePrice,
                    _maxPrice = givenRequest.maxPrice,
                    _limitOfEnrollment = givenRequest.limitOfEnrollment,
                    _offline = givenRequest.offline,
                    _free = givenRequest.free,
                    _name = givenRequest.name,
                    _beginEnrollmentDateTime = givenRequest.beginEnrollmentDateTime,
                    _closeEnrollmentDateTime = givenRequest.closeEnrollmentDateTime,
                    _beginEventDateTime = givenRequest.beginEventDateTime,
                    _endEventDateTime = givenRequest.endEventDateTime,
                )
                spyEventService.registerEventReturns = givenEvent
                mockMvc.perform(
                    post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(givenRequest))
                )
                spyEventService.registerEventArguments shouldBe givenEvent
            }
        }
    }
}
