package me.study.rest.event

import me.study.rest.util.ErrorField

class RegisterEventBadRequestException(
    override val message: String,
    val errorFields: List<ErrorField>
) : RuntimeException(message)
