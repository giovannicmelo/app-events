package br.com.giovannicampos.events.domain.data

import br.com.giovannicampos.events.domain.entities.Event
import br.com.giovannicampos.events.domain.entities.Person

interface EventRepositoryContract {

    suspend fun fetchAllEvents(): List<Event>

    suspend fun getEventDetails(eventId: String): Event

    suspend fun doCheckIn(person: Person)
}