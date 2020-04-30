package br.com.giovannicampos.appevents.events.data.api

import br.com.giovannicampos.core.data.models.ApiResponse
import br.com.giovannicampos.appevents.events.data.models.Event
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface EventsApi {

    @Headers("Content-Type: application/json")
    @GET("events")
    suspend fun getEvents(): Response<List<Event>>

    @Headers("Content-Type: application/json")
    @GET("events/{eventId}")
    suspend fun getEventById(@Path("eventId") eventId: String): Response<Event>

    @Headers("Content-Type: application/json")
    @POST("checkin")
    suspend fun postCheckIn(@Body person: Event.Person): Response<ApiResponse>
}