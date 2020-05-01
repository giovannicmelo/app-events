package br.com.giovannicampos.events.data.repository

import br.com.giovannicampos.core.data.models.ApiResponse
import br.com.giovannicampos.events.data.contracts.EventsDataSource
import br.com.giovannicampos.events.data.contracts.EventsRepository
import br.com.giovannicampos.events.data.models.Event
import retrofit2.Response

class EventsRepositoryImpl(private val dataSource: EventsDataSource.Remote) : EventsRepository {

    override suspend fun getEvents(): Response<List<Event>> {
        return dataSource.getEvents()
    }

    override suspend fun getEventById(eventId: String): Response<Event> {
        return dataSource.getEventById(eventId)
    }

    override suspend fun postCheckIn(person: Event.Person): Response<ApiResponse> {
        return dataSource.postCheckIn(person)
    }
}