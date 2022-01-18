package br.com.giovannicampos.events.domain.data

import br.com.giovannicampos.events.domain.entities.Event
import br.com.giovannicampos.events.domain.entities.Person

interface EventDataSource {

    suspend fun getEvents(): List<Event>

    suspend fun getEventById(eventId: String): Event

    suspend fun postCheckIn(person: Person)
}