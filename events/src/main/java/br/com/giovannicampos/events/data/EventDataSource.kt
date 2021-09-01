package br.com.giovannicampos.events.data

import br.com.giovannicampos.events.domain.Event
import br.com.giovannicampos.events.domain.Person

interface EventDataSource {

    suspend fun getEvents(): List<Event>

    suspend fun getEventById(eventId: String): Event

    suspend fun postCheckIn(person: Person)
}