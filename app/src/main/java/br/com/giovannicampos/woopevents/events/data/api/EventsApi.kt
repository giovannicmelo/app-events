package br.com.giovannicampos.woopevents.events.data.api

import br.com.giovannicampos.woopevents.base.data.models.ApiResponse
import br.com.giovannicampos.woopevents.events.data.models.Event
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface EventsApi {

    @Headers("Content-Type: application/json")
    @GET("events")
    suspend fun getEvent(): Response<List<Event>>

    @Headers("Content-Type: application/json")
    @GET("events/{eventId}")
    suspend fun getEventById(@Path("eventId") eventId: Int): Response<Event>

    @Headers("Content-Type: application/json")
    @POST("checkin")
    suspend fun postCheckIn(@Body person: Event.Person): Response<ApiResponse>
}