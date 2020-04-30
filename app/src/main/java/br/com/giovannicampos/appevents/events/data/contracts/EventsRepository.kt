package br.com.giovannicampos.appevents.events.data.contracts

import br.com.giovannicampos.core.data.models.ApiResponse
import br.com.giovannicampos.appevents.events.data.models.Event
import retrofit2.Response

interface EventsRepository {

    suspend fun getEvents(): Response<List<Event>>
    suspend fun getEventById(eventId: String): Response<Event>
    suspend fun postCheckIn(person: Event.Person): Response<ApiResponse>
}