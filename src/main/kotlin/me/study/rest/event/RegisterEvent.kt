package me.study.rest.event

import me.study.rest.event.Event.Status
import me.study.rest.event.Event.Status.DRAFT
import me.study.rest.util.ErrorField
import java.time.LocalDateTime

class RegisterEvent(
    val id: Long = 0,
    val description: String? = null,
    val location: String? = null,
    val basePrice: Int,
    val maxPrice: Int,
    val limitOfEnrollment: Int,
    val name: String,
    val beginEnrollmentDateTime: LocalDateTime,
    val closeEnrollmentDateTime: LocalDateTime,
    val beginEventDateTime: LocalDateTime,
    val endEventDateTime: LocalDateTime,
) {

    var offline: Boolean = false
        private set
    var free: Boolean = false
        private set
    var eventStatus: Status = DRAFT
        private set

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
        _eventStatus = eventStatus
    )

    fun validate() {
        checkPrice()
    }

    private fun checkPrice() {
        if (basePrice > 0 && maxPrice == 0 && limitOfEnrollment != 0)
            throw RegisterEventBadRequestException(
                message = "Base price higher than the highest price when there is a limit price",
                listOf(
                    ErrorField("basePrice", basePrice),
                    ErrorField("maxPrice", maxPrice),
                    ErrorField("limitOfEnrollment", limitOfEnrollment)
                )
            )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RegisterEvent

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    companion object {
        fun of(event: Event): RegisterEvent {
            val registerEvent = RegisterEvent(
                id = event.id,
                description = event.description,
                location = event.location,
                basePrice = event.basePrice,
                maxPrice = event.maxPrice,
                limitOfEnrollment = event.limitOfEnrollment,
                name = event.name,
                beginEnrollmentDateTime = event.beginEnrollmentDateTime,
                closeEnrollmentDateTime = event.closeEnrollmentDateTime,
                beginEventDateTime = event.beginEventDateTime,
                endEventDateTime = event.endEventDateTime
            )
            registerEvent.apply {
                offline = event.offline
                free = event.free
                eventStatus = event.eventStatus
            }

            return registerEvent
        }
    }
}
