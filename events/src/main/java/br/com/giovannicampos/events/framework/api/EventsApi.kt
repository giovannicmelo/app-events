package br.com.giovannicampos.events.framework.api

import br.com.giovannicampos.events.framework.models.EventModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface EventsApi {

    @Headers("Content-Type: application/json")
    @GET("events")
    suspend fun getEvents(): Response<List<EventModel>>

    @Headers("Content-Type: application/json")
    @GET("events/{eventId}")
    suspend fun getEventById(@Path("eventId") eventId: String): Response<EventModel>

    @Headers("Content-Type: application/json")
    @POST("checkin")
    suspend fun postCheckIn(@Body person: EventModel.PersonModel): Response<Void>
}