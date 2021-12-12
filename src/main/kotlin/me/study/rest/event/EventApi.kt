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
        val selfRel = linkTo(methodOn(EventApi::class.java).registerEvent(registeredEvent)).slash(registeredEvent.id).withSelfRel()
        val getRel = linkTo(methodOn(EventApi::class.java).registerEvent(registeredEvent)).withRel { "get" }
        val patchRel = linkTo(methodOn(EventApi::class.java).registerEvent(registerEvent)).slash(registeredEvent.id).withRel { "patch" }
        val registerEventEntityModel = EntityModel.of(registeredEvent,
            selfRel, getRel, patchRel)
        return ResponseEntity.created(registerEventEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(registerEventEntityModel)
    }

}
