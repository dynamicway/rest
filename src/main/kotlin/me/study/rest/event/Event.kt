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
    private var _name: String? = null,
    private var _beginEnrollmentDateTime: LocalDateTime? = null,
    private var _closeEnrollmentDateTime: LocalDateTime? = null,
    private var _beginEventDateTime: LocalDateTime? = null,
    private var _endEventDateTime: LocalDateTime? = null,
    @Enumerated(STRING)
    private var _eventStatus: Status? = null
) {

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
        get() = _name!!
    val beginEnrollmentDateTime
        get() = _beginEnrollmentDateTime!!
    val closeEnrollmentDateTime
        get() = _closeEnrollmentDateTime!!
    val beginEventDateTime
        get() = _beginEventDateTime!!
    val endEventDateTime
        get() = _endEventDateTime!!
    val eventStatus
        get() = _eventStatus!!

    enum class Status {
        DRAFT, PUBLISHED, BEGAN_ENROLLMENT
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Event

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }


}