package me.study.rest.event

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.hateoas.MediaTypes
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDateTime

internal class EventApiTest {

    private val eventApi = EventApi()

    private val mockMvc = MockMvcBuilders.standaloneSetup(eventApi).build()



    companion object {
        private val objectMapper = ObjectMapper()

        @BeforeAll
        @JvmStatic
        fun setUpObjectMapper() {
            objectMapper.registerModule(JavaTimeModule())
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        }
    }

    @Test
    fun createEvent_isCreated() {
        val givenEvent = Event(
            id = 0,
            name = "Spring",
            description = "Rest Api with Spring",
            beginEnrollmentDateTime = LocalDateTime.of(2021, 11, 22, 0, 0),
            closeEnrollmentDateTime = LocalDateTime.of(2021, 11, 23, 0, 0),
            beginEventDateTime = LocalDateTime.of(2021, 11, 24, 0, 0),
            endEventDateTime = LocalDateTime.of(2021, 11, 25, 0, 0),
            location = "Naver D2",
            basePrice = 100,
            maxPrice = 200,
            limitOfEnrollment = 100,
            offline = true,
            free = false,
            eventStatus = Event.Status.DRAFT
        )

        mockMvc.perform(post("/api/events")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaTypes.HAL_JSON)
            .content(objectMapper.writeValueAsString(givenEvent))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("id").exists())
    }
}
