package me.study.rest.event

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.test.TestCase
import me.study.rest.event.dto.EventDto
import org.springframework.hateoas.MediaTypes
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDateTime

internal class EventApiTest : BehaviorSpec() {
    private lateinit var eventApi: EventApi
    private val mockMvc: MockMvc by lazy { MockMvcBuilders.standaloneSetup(eventApi).build() }
    private val objectMapper: ObjectMapper by lazy {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }

    override fun beforeTest(testCase: TestCase) {
        eventApi = EventApi(
            SpyEventService()
        )
    }

    init {
        Given("EventRequest 가 주어졌을 때") {
            val givenRequest = EventDto(
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
            When("주어진 EventRequest를 갖고 Post: /api/events 호출") {
                val actualApiResult = mockMvc.perform(
                    post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(givenRequest))
                )
                Then("status 200, jsonPath 검증") {
                    actualApiResult
                        .andDo { println() }
                        .andExpect(status().isCreated)
                        .andExpect(jsonPath("id").exists())
                }
            }

        }
    }
}
