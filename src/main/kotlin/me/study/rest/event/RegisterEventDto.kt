package me.study.rest.event

import java.time.LocalDateTime

class RegisterEventDto(
    val id: Long = 0,
    val description: String? = null,
    val location: String? = null,
    val basePrice: Int,
    val maxPrice: Int,
    val limitOfEnrollment: Int,
    val offline: Boolean,
    val free: Boolean,
    val name: String,
    val beginEnrollmentDateTime: LocalDateTime,
    val closeEnrollmentDateTime: LocalDateTime,
    val beginEventDateTime: LocalDateTime,
    val endEventDateTime: LocalDateTime
) {
    fun toEntity() = Event(
        id = id,
        _description = description,
        _location = location,
        _basePrice = basePrice,
        _maxPrice = maxPrice,
        _limitOfEnrollment = limitOfEnrollment,
        _offline = offline,
        _free = free,
        _name = name,
        _beginEnrollmentDateTime = beginEnrollmentDateTime,
        _closeEnrollmentDateTime = closeEnrollmentDateTime,
        _beginEventDateTime = beginEventDateTime,
        _endEventDateTime = endEventDateTime,
    )

    companion object {
        fun of(event: Event) = RegisterEventDto(
            id = event.id,
            description = event.description,
            location = event.location,
            basePrice = event.basePrice,
            maxPrice = event.maxPrice,
            limitOfEnrollment = event.limitOfEnrollment,
            offline = event.offline,
            free = event.free,
            name = event.name,
            beginEnrollmentDateTime = event.beginEnrollmentDateTime,
            closeEnrollmentDateTime = event.closeEnrollmentDateTime,
            beginEventDateTime = event.beginEventDateTime,
            endEventDateTime = event.endEventDateTime
        )
    }
}
