package br.com.giovannicampos.events.framework.api

import br.com.giovannicampos.events.framework.models.EventModel
import br.com.giovannicampos.events.framework.models.PersonModel
import kotlinx.coroutines.delay
import okhttp3.internal.EMPTY_RESPONSE
import retrofit2.Response
import kotlin.random.Random

class EventsApiMock : EventsApi {
    override suspend fun getEvents(): Response<List<EventModel>> {
        delay(1000)
        return when {
            Random.nextBoolean() -> Response.success(
                listOf(
                    EventModel.mock()
                )
            )
            else -> Response.error(500, EMPTY_RESPONSE)
        }
    }

    override suspend fun getEventById(eventId: String): Response<EventModel> {
        delay(1000)
        return when {
            Random.nextBoolean() -> Response.success(EventModel.mock())
            else -> Response.error(500, EMPTY_RESPONSE)
        }
    }

    override suspend fun postCheckIn(person: PersonModel): Response<Unit> {
        return when {
            Random.nextBoolean() -> Response.success(Unit)
            else -> Response.error(500, EMPTY_RESPONSE)
        }
    }
}