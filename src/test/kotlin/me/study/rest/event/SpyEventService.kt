package me.study.rest.event

class SpyEventService : EventService {
    lateinit var registerEventReturns: Event
    lateinit var registerEventArguments: Event
    override fun registerEvent(registerEvent: Event): Event {
        registerEventArguments = registerEvent
        return registerEventReturns
    }
}
