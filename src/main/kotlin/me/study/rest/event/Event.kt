package me.study.rest.event

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.EnumType.STRING
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Event(
    @Id
    @GeneratedValue
    val id: Long = 0,
    private var _description: String? = null,
    private var _location: String? = null,
    private var _basePrice: Int? = null,
    private var _maxPrice: Int? = null,
    private var _limitOfEnrollment: Int? = null,
    private var _offline: Boolean? = null,
    private var _free: Boolean? = null,
) {
    private lateinit var _name: String
    private lateinit var _beginEnrollmentDateTime: LocalDateTime
    private lateinit var _closeEnrollmentDateTime: LocalDateTime
    private lateinit var _beginEventDateTime: LocalDateTime
    private lateinit var _endEventDateTime: LocalDateTime


    @Enumerated(STRING)
    private lateinit var _eventStatus: Status

    val description
        get() = _description
    val location
        get() = _location
    val basePrice
        get() = _basePrice!!
    val maxPrice
        get() = _maxPrice!!
    val limitOfEnrollment
        get() = _limitOfEnrollment!!
    val offline
        get() = _offline!!
    val free
        get() = _free!!
    val name
        get() = _name
    val beginEnrollmentDateTime
        get() = _beginEnrollmentDateTime
    val closeEnrollmentDateTime
        get() = _closeEnrollmentDateTime
    val beginEventDateTime
        get() = _beginEventDateTime
    val endEventDateTime
        get() = _endEventDateTime

    enum class Status {
        DRAFT, PUBLISHED, BEGAN_ENROLLMENT
    }

}