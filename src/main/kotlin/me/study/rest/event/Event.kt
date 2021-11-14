package me.study.rest.event

import java.time.LocalDateTime

class Event(
    private var id: Long,
    private var name: String,
    private var description: String,
    private var beginEnrollmentDateTime: LocalDateTime,
    private var closeEnrollmentDateTime: LocalDateTime,
    private var beginEventDateTime: LocalDateTime,
    private var endEventDateTime: LocalDateTime,
    private var location: String,
    private var basePrice: Int,
    private var maxPrice: Int,
    private var limitOfEnrollment: Int,
    private var offline: Boolean,
    private var free: Boolean,
    private var eventStatus: Status
) {

    enum class Status {
        DRAFT, PUBLISHED, BEGAN_ENROLLMENT
    }

}