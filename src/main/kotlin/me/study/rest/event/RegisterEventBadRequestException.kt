package me.study.rest.event

import me.study.rest.event.RegisterEventBadRequestException.Cause.*
import me.study.rest.util.ErrorField

class RegisterEventBadRequestException(
    val errorCause: Cause,
    private val registerEvent: RegisterEvent
) : RuntimeException(errorCause.message) {
    override val message
    get() = errorCause.message
    val errorFields: List<ErrorField> by lazy { initErrorFields() }

    enum class Cause(
        val message: String
    ) {
        BASE_PRICE_HIGHER_THAN_THE_HIGHEST_PRICE("Base price higher than the highest price when there is a limit price"),
        NEGATIVE_PRICE("negative price"),
        NEGATIVE_ENROLLMENT("negative enrollment")
    }

    private fun initErrorFields(): List<ErrorField> {
        return when (errorCause) {
            BASE_PRICE_HIGHER_THAN_THE_HIGHEST_PRICE -> listOf(
                ErrorField(BASE_PRICE, registerEvent.basePrice),
                ErrorField(MAX_PRICE, registerEvent.maxPrice),
                ErrorField(LIMIT_OF_ENROLLMENT, registerEvent.limitOfEnrollment)
            )
            NEGATIVE_PRICE -> listOf(
                ErrorField(BASE_PRICE, registerEvent.basePrice),
                ErrorField(MAX_PRICE, registerEvent.maxPrice)
            )
            NEGATIVE_ENROLLMENT -> listOf(
                ErrorField(LIMIT_OF_ENROLLMENT, registerEvent.limitOfEnrollment)
            )
        }
    }

    private companion object Field {
        private const val BASE_PRICE = "basePrice"
        private const val MAX_PRICE = "maxPrice"
        private const val LIMIT_OF_ENROLLMENT = "limitOfEnrollment"
    }
}
