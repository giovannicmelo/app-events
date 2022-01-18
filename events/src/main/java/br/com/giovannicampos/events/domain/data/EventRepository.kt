package br.com.giovannicampos.events.domain.data

import br.com.giovannicampos.events.domain.entities.Event
import br.com.giovannicampos.events.domain.entities.Person

class EventRepository(private val dataSource: EventDataSource) : EventRepositoryContract {

    override suspend fun fetchAllEvents(): List<Event> = dataSource.getEvents()

    override suspend fun getEventDetails(eventId: String): Event = dataSource.getEventById(eventId)

    override suspend fun doCheckIn(person: Person) = dataSource.postCheckIn(person)
}