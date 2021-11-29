package me.study.rest.event

import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/events", produces = [MediaTypes.HAL_JSON_VALUE])
class EventApi(
    private val eventService: EventService
) {

    @PostMapping
    @ResponseStatus(CREATED)
    fun registerEvent(
        @RequestBody registerEvent: RegisterEvent
    ): ResponseEntity<EntityModel<RegisterEvent>> {
        val registeredEvent = eventService.registerEvent(registerEvent)
        val selfRel = linkTo(methodOn(EventApi::class.java).registerEvent(registeredEvent)).withSelfRel()
        val getRel = linkTo(methodOn(EventApi::class.java).registerEvent(registeredEvent)).slash(registeredEvent.id)
            .withRel { "get" }
        val registerEventEntityModel = EntityModel.of(registeredEvent, selfRel, getRel)
        return ResponseEntity.created(registerEventEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(registerEventEntityModel)
    }

}
