package me.study.rest.event

import java.time.LocalDateTime

class Event(
    var id: Long,
    var name: String,
    var description: String,
    var beginEnrollmentDateTime: LocalDateTime,
    var closeEnrollmentDateTime: LocalDateTime,
    var beginEventDateTime: LocalDateTime,
    var endEventDateTime: LocalDateTime,
    var location: String?,
    var basePrice: Int,
    var maxPrice: Int,
    var limitOfEnrollment: Int,
    var offline: Boolean,
    var free: Boolean,
    var eventStatus: Status
) {

    enum class Status {
        DRAFT, PUBLISHED, BEGAN_ENROLLMENT
    }

}