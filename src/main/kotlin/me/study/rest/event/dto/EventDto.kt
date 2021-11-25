package me.study.rest.event.dto

import java.time.LocalDateTime

class EventDto(
    val id: Long = 0,
    val description: String? = null,
    val location: String? = null,
    val basePrice: Int,
    val maxPrice: Int,
    val limitOfEnrollment: Int,
    val offline:Boolean,
    val free:Boolean,
    val name:String,
    val beginEnrollmentDateTime: LocalDateTime,
    val closeEnrollmentDateTime: LocalDateTime,
    val beginEventDateTime: LocalDateTime,
    val endEventDateTime: LocalDateTime
)