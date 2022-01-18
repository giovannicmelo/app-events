package br.com.giovannicampos.events.framework

import br.com.giovannicampos.events.domain.data.EventDataSource
import br.com.giovannicampos.events.domain.entities.Event
import br.com.giovannicampos.events.domain.entities.Person
import br.com.giovannicampos.events.framework.api.EventsApi
import br.com.giovannicampos.events.framework.mappers.EventMapper
import br.com.giovannicampos.events.framework.mappers.PersonMapper

class RetrofitEventDataSource(private val api: EventsApi) : EventDataSource {

    override suspend fun getEvents(): List<Event> {
        val events = api.getEvents()
        return if (events.isSuccessful) {
            events.body()?.map { model ->
                EventMapper.toEntity(model)
            } ?: listOf()
        } else {
            listOf()
        }
    }

    override suspend fun getEventById(eventId: String): Event {
        val event = api.getEventById(eventId)
        return if (event.isSuccessful) {
            event.body()?.let { model ->
                EventMapper.toEntity(model)
            } ?: Event()
        } else {
            Event()
        }
    }

    override suspend fun postCheckIn(person: Person) {
        val model = PersonMapper.toModel(person)
        api.postCheckIn(model)
    }
}