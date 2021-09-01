package br.com.giovannicampos.events.data

import br.com.giovannicampos.events.domain.Event
import br.com.giovannicampos.events.domain.Person

interface EventRepositoryContract {

    suspend fun fetchAllEvents(): List<Event>

    suspend fun getEventDetails(eventId: String): Event

    suspend fun doCheckIn(person: Person)
}