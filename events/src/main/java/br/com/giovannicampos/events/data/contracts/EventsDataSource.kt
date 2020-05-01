package br.com.giovannicampos.events.data.contracts

import br.com.giovannicampos.core.data.models.ApiResponse
import br.com.giovannicampos.events.data.models.Event
import retrofit2.Response

interface EventsDataSource {

    interface Remote {

        suspend fun getEvents(): Response<List<Event>>
        suspend fun getEventById(eventId: String): Response<Event>
        suspend fun postCheckIn(person: Event.Person): Response<ApiResponse>
    }
}