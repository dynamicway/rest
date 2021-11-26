package me.study.rest.event

class SpyEventService : EventService {
    lateinit var registerEvent_returns: Event
    override fun registerEvent(): Event = registerEvent_returns

}
