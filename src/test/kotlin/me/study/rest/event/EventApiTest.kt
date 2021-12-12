package me.study.rest.event

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.shouldBe
import me.study.rest.event.testdouble.SpyEventService
import me.study.rest.util.ErrorField
import me.study.rest.util.ErrorResponse
import org.springframework.hateoas.MediaTypes.HAL_JSON
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultHandler
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDateTime

internal class EventApiTest : ShouldSpec() {
    private lateinit var eventApi: EventApi
    private lateinit var spyEventService: SpyEventService
    private val mockMvc: MockMvc by lazy {
        MockMvcBuilders.standaloneSetup(eventApi)
            .setControllerAdvice(EventApiExceptionResponder())
            .build()
    }
    private val objectMapper: ObjectMapper by lazy {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }

    override fun beforeContainer(testCase: TestCase) {
        spyEventService = SpyEventService()
        eventApi = EventApi(
            spyEventService
        )
    }

    init {
        context("registerEvent") {
            should("응답 검증") {
                val givenRequest = RegisterEvent(
                    basePrice = 0,
                    maxPrice = 0,
                    limitOfEnrollment = 0,
                    name = "",
                    beginEnrollmentDateTime = LocalDateTime.of(2021, 1, 1, 1, 1),
                    closeEnrollmentDateTime = LocalDateTime.of(2021, 1, 1, 1, 1, 1),
                    beginEventDateTime = LocalDateTime.of(2021, 1, 1, 1, 1, 1),
                    endEventDateTime = LocalDateTime.of(2021, 1, 1, 1, 1, 1)
                )
                spyEventService.registerEventReturns = givenRequest
                mockMvc.perform(
                    post("/api/events")
                        .contentType(APPLICATION_JSON)
                        .accept(HAL_JSON)
                        .content(objectMapper.writeValueAsString(givenRequest))
                )
                    .andExpect(status().isCreated)
                    .andExpect(header().exists(HttpHeaders.LOCATION))
                    .andExpect(jsonPath("links.length()").value(3))
                    .andExpect(jsonPath("links[0].rel").value("self"))
                    .andExpect(jsonPath("links[1].rel").value("get"))
                    .andExpect(jsonPath("links[2].rel").value("patch"))
                    .andExpect(jsonPath("id").exists())
            }
            should("행위 검증") {
                val givenRegisterEvent = RegisterEvent(
                    basePrice = 0,
                    maxPrice = 0,
                    limitOfEnrollment = 0,
                    name = "",
                    beginEnrollmentDateTime = LocalDateTime.of(2021, 1, 1, 1, 1),
                    closeEnrollmentDateTime = LocalDateTime.of(2021, 1, 1, 1, 1, 1),
                    beginEventDateTime = LocalDateTime.of(2021, 1, 1, 1, 1, 1),
                    endEventDateTime = LocalDateTime.of(2021, 1, 1, 1, 1, 1)
                )
                spyEventService.registerEventReturns = givenRegisterEvent
                mockMvc.perform(
                    post("/api/events")
                        .contentType(APPLICATION_JSON)
                        .accept(HAL_JSON)
                        .content(objectMapper.writeValueAsString(givenRegisterEvent))
                )
                spyEventService.registerEventArguments shouldBe givenRegisterEvent
            }
            should("RegisterEventBadRequestException 이 터지면 BadRequest 반환") {
                val givenLocalDateTime = LocalDateTime.of(2021, 12, 25, 0, 0, 0)
                val givenRegisterEvent = RegisterEvent(
                    basePrice = 0,
                    maxPrice = 0,
                    limitOfEnrollment = 0,
                    name = "",
                    beginEnrollmentDateTime = givenLocalDateTime,
                    closeEnrollmentDateTime = givenLocalDateTime,
                    beginEventDateTime = givenLocalDateTime,
                    endEventDateTime = givenLocalDateTime
                )
                val givenMessage = "Base price higher than the highest price when there is a limit price"
                val givenErrorFields = listOf(
                    ErrorField("basePrice", 0),
                    ErrorField("maxPrice", 0),
                    ErrorField("limitOfEnrollment", 0)
                )
                spyEventService.registerEventException = RegisterEventBadRequestException(
                    errorCause = RegisterEventBadRequestException.Cause.BASE_PRICE_HIGHER_THAN_THE_HIGHEST_PRICE,
                    givenRegisterEvent
                )
                val errorResponse = ErrorResponse(
                    givenMessage,
                    givenErrorFields
                )

                mockMvc.perform(
                    post("/api/events")
                        .contentType(APPLICATION_JSON)
                        .accept(HAL_JSON)
                        .content(objectMapper.writeValueAsString(givenRegisterEvent))
                )
                    .andExpect(status().isBadRequest)
                    .andExpect(content().contentType(HAL_JSON))
                    .andExpect(content().json(objectMapper.writeValueAsString(errorResponse)))
            }
        }
    }
}
