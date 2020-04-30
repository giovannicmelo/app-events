package br.com.giovannicampos.appevents.events.data.remote

import br.com.giovannicampos.appevents.base.data.models.ApiResponse
import br.com.giovannicampos.appevents.events.data.api.EventsApi
import br.com.giovannicampos.appevents.events.data.contracts.EventsDataSource
import br.com.giovannicampos.appevents.events.data.models.Event
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class EventsRemoteDataSourceTest {

    private lateinit var apiMock: EventsApi
    private lateinit var remoteDataSource: EventsDataSource.Remote

    @Before
    fun setUp() {
        apiMock = mock()
        remoteDataSource = EventsRemoteDataSource(apiMock)
    }

    @Test
    fun `Get event, when it is requested to retrieve all events, then returns a list of events`() = runBlocking {
        // ARRANGE
        val expectedPerson = Event.Person(
            id = "1",
            name = "Test Person"
        )
        val expectedCoupon = Event.Coupon(
            id = "1",
            eventId = "1",
            discount = 10
        )
        val expectedEventsList = listOf(
            Event(
                id = "1",
                date = 1534784400000,
                title = "Event 1",
                description = "Test event 1",
                price = 10.00,
                latitude = -51.2146267,
                longitude = -30.0392981,
                listCoupons = listOf(expectedCoupon),
                listPersons = listOf(expectedPerson)
            )
        )

        val expectedResponseEvents: Response<List<Event>> = Response.success(expectedEventsList)
        whenever(apiMock.getEvents()).thenReturn(expectedResponseEvents)

        // ACT
        val actualResponseEvents: Response<List<Event>> = remoteDataSource.getEvents()

        // ASSERT
        Assert.assertEquals(expectedResponseEvents, actualResponseEvents)
    }

    @Test
    fun `Get event, when it is requested to retrieve all events, then returns an api error message`() = runBlocking {
        // ARRANGE
        val expectedResponseError = "Bad request"
        val expectedResponseBody: ResponseBody = ResponseBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            Gson().toJson(expectedResponseError)
        )
        val expectedResponseEvents = Response.error<List<Event>>(400, expectedResponseBody)

        whenever(apiMock.getEvents()).thenReturn(expectedResponseEvents)

        // ACT
        val actualResponseEvents: Response<List<Event>> = remoteDataSource.getEvents()

        // ASSERT
        Assert.assertEquals(expectedResponseEvents, actualResponseEvents)
    }

    @Test
    fun `Get event by id, when it is passed an event id, then returns an event`() = runBlocking {
        // ARRANGE
        val expectedEventId = "1"
        val expectedPerson = Event.Person(
            id = "1",
            name = "Test Person"
        )
        val expectedCoupon = Event.Coupon(
            id = "1",
            eventId = expectedEventId,
            discount = 10
        )
        val expectedEvent = Event(
            id = expectedEventId,
            date = 1534784400000,
            title = "Event 1",
            description = "Test event 1",
            price = 10.00,
            latitude = -51.2146267,
            longitude = -30.0392981,
            listCoupons = listOf(expectedCoupon),
            listPersons = listOf(expectedPerson)
        )

        val expectedResponseEvent: Response<Event> = Response.success(expectedEvent)
        whenever(apiMock.getEventById(expectedEventId)).thenReturn(expectedResponseEvent)

        // ACT
        val actualResponseEvent: Response<Event> = remoteDataSource.getEventById(expectedEventId)

        // ASSERT
        Assert.assertEquals(expectedResponseEvent, actualResponseEvent)
    }

    @Test
    fun `Get event by id, when it is passed an event id, then returns an api error message`() = runBlocking {
        // ARRANGE
        val expectedEventId = ""
        val expectedResponseError = "Bad request"
        val expectedResponseBody: ResponseBody = ResponseBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            Gson().toJson(expectedResponseError)
        )
        val expectedResponseEvent = Response.error<Event>(400, expectedResponseBody)
        whenever(apiMock.getEventById(expectedEventId)).thenReturn(expectedResponseEvent)

        // ACT
        val actualResponseEvent: Response<Event> = remoteDataSource.getEventById(expectedEventId)

        // ASSERT
        Assert.assertEquals(expectedResponseEvent, actualResponseEvent)
    }

    @Test
    fun `Post check in, when it is passed a person, then returns a status that was successful`() = runBlocking {
        // ARRANGE
        val expectedPerson = Event.Person(
            id = "1",
            name = "Test Person",
            email = "person@mail.to.me"
        )
        val expectedCheckResponse = ApiResponse("201")
        val expectedResponseApi: Response<ApiResponse> = Response.success(expectedCheckResponse)
        whenever(apiMock.postCheckIn(expectedPerson)).thenReturn(expectedResponseApi)

        // ACT
        val actualResponseApi = remoteDataSource.postCheckIn(expectedPerson)

        // ASSERT
        Assert.assertEquals(expectedResponseApi, actualResponseApi)
    }

    @Test
    fun `Post check in, when it is passed a person, then returns an api error message`() = runBlocking {
        // ARRANGE
        val expectedPerson = Event.Person()
        val expectedResponseError = "Bad request"
        val expectedResponseBody: ResponseBody = ResponseBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            Gson().toJson(expectedResponseError)
        )
        val expectedResponseApi = Response.error<ApiResponse>(400, expectedResponseBody)
        whenever(apiMock.postCheckIn(expectedPerson)).thenReturn(expectedResponseApi)

        // ACT
        val actualResponseApi = remoteDataSource.postCheckIn(expectedPerson)

        // ASSERT
        Assert.assertEquals(expectedResponseApi, actualResponseApi)
    }
}