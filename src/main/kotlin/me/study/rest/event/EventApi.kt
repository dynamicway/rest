package me.study.rest.event

import me.study.rest.event.dto.EventDto
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/events", produces = [MediaTypes.HAL_JSON_VALUE])
class EventApi(
    private val eventService: EventService
) {

    @PostMapping
    fun createEvents(
        @RequestBody eventDto: EventDto
    ): ResponseEntity<EventDto> {
        val uri = linkTo<EventApi> { createEvents(eventDto) }.slash("{id}").toUri()
        return ResponseEntity.created(uri).body(eventDto)
    }

}
