package me.study.rest.event.testdouble

import me.study.rest.event.EventService
import me.study.rest.event.RegisterEvent

class SpyEventService : EventService {
    lateinit var registerEventReturns: RegisterEvent
    lateinit var registerEventArguments: RegisterEvent
    var registerEventException: Throwable? = null
    override fun registerEvent(registerEvent: RegisterEvent): RegisterEvent {
        registerEventArguments = registerEvent
        registerEventException?.let { throw it }
        return registerEventReturns
    }
}
