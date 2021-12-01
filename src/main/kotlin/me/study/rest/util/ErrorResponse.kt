package me.study.rest.util

class ErrorResponse(
    val message: String,
    val fields: List<ErrorField>? = null
)
