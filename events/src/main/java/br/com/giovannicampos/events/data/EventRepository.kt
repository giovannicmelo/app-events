package br.com.giovannicampos.events.data

import br.com.giovannicampos.events.domain.Event
import br.com.giovannicampos.events.domain.Person

class EventRepository(private val dataSource: EventDataSource) : EventRepositoryContract {

    override suspend fun fetchAllEvents(): List<Event> = dataSource.getEvents()

    override suspend fun getEventDetails(eventId: String): Event = dataSource.getEventById(eventId)

    override suspend fun doCheckIn(person: Person) = dataSource.postCheckIn(person)
}