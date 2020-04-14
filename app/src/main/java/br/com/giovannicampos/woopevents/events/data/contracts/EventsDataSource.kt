package br.com.giovannicampos.woopevents.events.data.contracts

import br.com.giovannicampos.woopevents.base.data.models.ApiResponse
import br.com.giovannicampos.woopevents.events.data.models.Event
import retrofit2.Response

interface EventsDataSource {

    interface Remote {

        suspend fun getEvent(): Response<List<Event>>
        suspend fun getEventById(eventId: Int): Response<Event>
        suspend fun postCheckIn(person: Event.Person): Response<ApiResponse>
    }
}