package br.com.giovannicampos.woopevents.events.data.remote

import br.com.giovannicampos.woopevents.base.data.models.ApiResponse
import br.com.giovannicampos.woopevents.events.data.api.EventsApi
import br.com.giovannicampos.woopevents.events.data.contracts.EventsDataSource
import br.com.giovannicampos.woopevents.events.data.models.Event
import retrofit2.Response

class EventsRemoteDataSource(private val api: EventsApi) : EventsDataSource.Remote {

    override suspend fun getEvent(): Response<List<Event>> {
        return api.getEvent()
    }

    override suspend fun getEventById(eventId: Int): Response<Event> {
        return api.getEventById(eventId)
    }

    override suspend fun postCheckIn(person: Event.Person): Response<ApiResponse> {
        return api.postCheckIn(person)
    }
}