package br.com.giovannicampos.appevents.events.data.remote

import br.com.giovannicampos.appevents.base.data.models.ApiResponse
import br.com.giovannicampos.appevents.events.data.api.EventsApi
import br.com.giovannicampos.appevents.events.data.contracts.EventsDataSource
import br.com.giovannicampos.appevents.events.data.models.Event
import retrofit2.Response

class EventsRemoteDataSource(private val api: EventsApi) : EventsDataSource.Remote {

    override suspend fun getEvents(): Response<List<Event>> {
        return api.getEvents()
    }

    override suspend fun getEventById(eventId: String): Response<Event> {
        return api.getEventById(eventId)
    }

    override suspend fun postCheckIn(person: Event.Person): Response<ApiResponse> {
        return api.postCheckIn(person)
    }
}