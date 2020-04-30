package br.com.giovannicampos.appevents.events.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.giovannicampos.appevents.events.CoroutinesTestRule
import br.com.giovannicampos.core.command.models.GenericCommand
import br.com.giovannicampos.core.command.interfaces.CommandProvider
import br.com.giovannicampos.core.data.models.ApiResponse
import br.com.giovannicampos.core.ui.SingleLiveEvent
import br.com.giovannicampos.appevents.events.data.contracts.EventsRepository
import br.com.giovannicampos.appevents.events.data.models.Event
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.Rule
import retrofit2.Response

class EventDetailsViewModelTest {

    private lateinit var commandMock: SingleLiveEvent<GenericCommand>
    private lateinit var commandProviderMock: CommandProvider
    private lateinit var viewStateObserver: Observer<EventDetailsViewModel.ViewState>
    private lateinit var viewModel: EventDetailsViewModel
    private lateinit var repositoryMock: EventsRepository

    @JvmField @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule =
        CoroutinesTestRule()

    @Before
    fun setUp() {
        commandProviderMock = mock()
        commandMock = mock()
        viewStateObserver = mock()
        repositoryMock = mock()

        whenever(commandProviderMock.getCommand()).thenReturn(commandMock)

        viewModel = EventDetailsViewModel(commandProviderMock, repositoryMock)
    }

    @Test
    fun `Get event by id, when it is passed an event id, then show a event`() = runBlocking {
        // ARRANGE
        val expectedViewState = EventDetailsViewModel.ViewState()
        viewModel.viewState.observeForever(viewStateObserver)

        val expectedEventId = "1"
        val expectedPerson = Event.Person(
            id = "1",
            name = "Test Person",
            eventId = expectedEventId
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

        val expectedCommand = EventDetailsViewModel.Command.ShowEventDetails(expectedEvent)
        val expectedResponse: Response<Event> = Response.success(expectedEvent)
        whenever(repositoryMock.getEventById(expectedEventId)).thenReturn(expectedResponse)

        // ACT
        viewModel.getEventById(expectedEventId)

        // ASSERT
        verify(viewStateObserver, times(1)).onChanged(expectedViewState.copy(isLoading = false))
        verify(repositoryMock, times(1)).getEventById(expectedEventId)

        val commandCaptor = argumentCaptor<EventDetailsViewModel.Command.ShowEventDetails>()
        verify(commandMock, times(1)).postValue(commandCaptor.capture())

        Assert.assertEquals(expectedCommand.event, expectedEvent)
    }

    @Test
    fun `Get event by id, when it is passed an event id, then show exception error message`() = runBlocking {
        // ARRANGE
        val expectedEventId = ""
        val expectedError = "Exception message"
        val expectedViewState = EventDetailsViewModel.ViewState()
        viewModel.viewState.observeForever(viewStateObserver)

        val expectedCommand = EventDetailsViewModel.Command.ShowExceptionMessage(expectedError)

        whenever(repositoryMock.getEventById(expectedEventId)).then {
            throw Exception(expectedError)
        }
        // ACT
        viewModel.getEventById(expectedEventId)

        // ASSERT
        verify(viewStateObserver, times(1)).onChanged(expectedViewState.copy(isLoading = false))
        verify(repositoryMock, times(1)).getEventById(expectedEventId)

        val commandCaptor = argumentCaptor<EventDetailsViewModel.Command.ShowExceptionMessage>()
        verify(commandMock, times(1)).postValue(commandCaptor.capture())

        Assert.assertEquals(expectedCommand.message, expectedError)
    }

    @Test
    fun `Get event by id, when it is passed an event id, then show api error message`() = runBlocking {
        // ARRANGE
        val expectedEventId = ""
        val expectedViewState = EventDetailsViewModel.ViewState()
        viewModel.viewState.observeForever(viewStateObserver)

        val expectedResponseError = "Not Found"
        val expectedResponseBody: ResponseBody = ResponseBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            Gson().toJson(expectedResponseError)
        )

        val expectedCommand = EventDetailsViewModel.Command.ShowApiErrorMessage
        val expectedResponseEvents = Response.error<Event>(404, expectedResponseBody)
        whenever(repositoryMock.getEventById(expectedEventId)).thenReturn(expectedResponseEvents)

        // ACT
        viewModel.getEventById(expectedEventId)

        // ASSERT
        verify(viewStateObserver, times(1)).onChanged(expectedViewState.copy(isLoading = false))
        verify(repositoryMock, times(1)).getEventById(expectedEventId)

        verify(commandMock, times(1)).postValue(expectedCommand)
    }

    @Test
    fun `Check connection network, when it is requested to check connection network, then show no connection message`() {
        // ARRANGE
        val expectedCommand = EventDetailsViewModel.Command.ShowNoConnectionMessage
        val expectedViewState = EventDetailsViewModel.ViewState()
        viewModel.viewState.observeForever(viewStateObserver)

        // ACT
        viewModel.showNoConnectionNetwork()

        // ASSERT
        verify(viewStateObserver, times(1)).onChanged(expectedViewState.copy(isLoading = false))

        verify(commandMock, times(1)).value = expectedCommand
    }

    @Test
    fun `Open share content event, when it is requested to open share content event, then verify if command was triggered`() {
        // ARRANGE
        val expectedCommand = EventDetailsViewModel.Command.ShareContentEvent

        // ACT
        viewModel.openShareContentEvent()

        // ASSERT
        verify(commandMock, times(1)).value = expectedCommand
    }

    @Test
    fun `Open check in modal, when it is requested to open modal of check in, then verify if command was triggered`() {
        // ARRANGE
        val expectedCommand = EventDetailsViewModel.Command.OpenCheckInFormModal

        // ACT
        viewModel.openCheckInModal()

        // ASSERT
        verify(commandMock, times(1)).value = expectedCommand
    }

    @Test
    fun `Do check in, when it is passed and name and e-mail, then validate check in successfully`() = runBlocking {
        // ARRANGE
        val expectedEventId = "1"
        val expectedName = "Test Person"
        val expectedEmail = "person@email.com"
        val expectedPerson = Event.Person(
            name = expectedName,
            email = expectedEmail,
            eventId = expectedEventId
        )
        val expectedResponseApi =
            ApiResponse("201")
        val expectedResponse: Response<ApiResponse> = Response.success(expectedResponseApi)
        whenever(repositoryMock.postCheckIn(expectedPerson)).thenReturn(expectedResponse)

        // ACT
        viewModel.doCheckIn(expectedEventId, expectedName, expectedEmail)

        // ASSERT
        verify(repositoryMock, times(1)).postCheckIn(expectedPerson)

        val expectedCommand = EventDetailsViewModel.Command.CheckInAccomplished
        verify(commandMock, times(1)).postValue(expectedCommand)
    }

    @Test
    fun `Do check in, when it is passed and name and e-mail, then validate check in failed`() = runBlocking {
        // ARRANGE
        val expectedEventId = ""
        val expectedName = ""
        val expectedEmail = ""
        val expectedPerson = Event.Person(
            name = expectedName,
            email = expectedEmail,
            eventId = expectedEventId
        )
        val expectedResponseError = "Not Found"
        val expectedResponseBody: ResponseBody = ResponseBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            Gson().toJson(expectedResponseError)
        )

        val expectedCommand = EventDetailsViewModel.Command.ShowApiErrorMessage
        val expectedResponseCheckIn = Response.error<ApiResponse>(404, expectedResponseBody)
        whenever(repositoryMock.postCheckIn(expectedPerson)).thenReturn(expectedResponseCheckIn)

        // ACT
        viewModel.doCheckIn(expectedEventId, expectedName, expectedEmail)

        // ASSERT
        verify(repositoryMock, times(1)).postCheckIn(expectedPerson)
        verify(commandMock, times(1)).postValue(expectedCommand)
    }

    @Test
    fun `Do check in, when it is passed and name and e-mail, then show exception error message`() = runBlocking {
        // ARRANGE
        val expectedEventId = ""
        val expectedName = ""
        val expectedEmail = ""
        val expectedPerson = Event.Person(
            name = expectedName,
            email = expectedEmail,
            eventId = expectedEventId
        )
        val expectedError = "Exception message"
        val expectedCommand = EventDetailsViewModel.Command.ShowExceptionMessage(expectedError)

        whenever(repositoryMock.postCheckIn(expectedPerson)).then {
            throw Exception(expectedError)
        }
        // ACT
        viewModel.doCheckIn(expectedEventId, expectedName, expectedEmail)

        // ASSERT
        verify(repositoryMock, times(1)).postCheckIn(expectedPerson)

        val commandCaptor = argumentCaptor<EventDetailsViewModel.Command.ShowExceptionMessage>()
        verify(commandMock, times(1)).postValue(commandCaptor.capture())

        Assert.assertEquals(expectedCommand.message, expectedError)
    }
}