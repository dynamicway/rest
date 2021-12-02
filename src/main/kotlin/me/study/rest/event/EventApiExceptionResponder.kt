package me.study.rest.event

import me.study.rest.util.ErrorResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(basePackageClasses = [EventApi::class])
class EventApiExceptionResponder(
    private val LOGGER: Logger = LoggerFactory.getLogger(EventApiExceptionResponder::class.java)
) {

    @ExceptionHandler(RegisterEventBadRequestException::class)
    @ResponseStatus(BAD_REQUEST)
    fun registerEventBadRequestException(registerEventBadRequestException: RegisterEventBadRequestException): ErrorResponse {
        LOGGER.error(registerEventBadRequestException.message, registerEventBadRequestException)
        return ErrorResponse(
            registerEventBadRequestException.message,
            registerEventBadRequestException.errorFields
        )
    }

}
