package br.com.giovannicampos.appevents.events.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.giovannicampos.appevents.CoroutinesTestRule
import br.com.giovannicampos.appevents.base.command.models.GenericCommand
import br.com.giovannicampos.appevents.base.command.providers.CommandProvider
import br.com.giovannicampos.appevents.base.data.models.ApiResponse
import br.com.giovannicampos.appevents.base.ui.SingleLiveEvent
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
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class EventsViewModelTest {

    private lateinit var commandMock: SingleLiveEvent<GenericCommand>
    private lateinit var commandProviderMock: CommandProvider
    private lateinit var viewStateObserver: Observer<EventsViewModel.ViewState>
    private lateinit var viewModel: EventsViewModel
    private lateinit var repositoryMock: EventsRepository

    @JvmField @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setUp() {
        commandProviderMock = mock()
        commandMock = mock()
        viewStateObserver = mock()
        repositoryMock = mock()

        whenever(commandProviderMock.getCommand()).thenReturn(commandMock)

        viewModel = EventsViewModel(commandProviderMock, repositoryMock)
    }

    @Test
    fun `Get events, when it is requested to get events, then show list of events`() = runBlocking {
        // ARRANGE
        val expectedViewState = EventsViewModel.ViewState()
        viewModel.viewState.observeForever(viewStateObserver)

        val expectedPerson = Event.Person(
            id = 1,
            name = "Test Person"
        )
        val expectedCoupon = Event.Coupon(
            id = 1,
            eventId = 1,
            discount = 10
        )
        val expectedEventsList = listOf(
            Event(
                id = 1,
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

        val expectedCommand = EventsViewModel.Command.ShowEvents(expectedEventsList)
        val expectedResponse: Response<List<Event>> = Response.success(expectedEventsList)
        whenever(repositoryMock.getEvents()).thenReturn(expectedResponse)

        // ACT
        viewModel.getEvents()

        // ASSERT
        verify(viewStateObserver, times(1)).onChanged(expectedViewState.copy(isLoading = false))
        verify(repositoryMock, times(1)).getEvents()

        val commandCaptor = argumentCaptor<EventsViewModel.Command.ShowEvents>()
        verify(commandMock, times(1)).postValue(commandCaptor.capture())

        Assert.assertEquals(expectedCommand.events, expectedEventsList)
    }

    @Test
    fun `Get events, when it is requested to get events, then show empty error message`() = runBlocking {
        // ARRANGE
        val expectedViewState = EventsViewModel.ViewState()
        viewModel.viewState.observeForever(viewStateObserver)

        val expectedEventsList = emptyList<Event>()
        val expectedResponse = Response.success(expectedEventsList)

        whenever(repositoryMock.getEvents()).thenReturn(expectedResponse)

        // ACT
        viewModel.getEvents()

        // ASSERT
        verify(viewStateObserver, times(1)).onChanged(expectedViewState.copy(isLoading = false))
        verify(repositoryMock, times(1)).getEvents()

        val commandCaptor = argumentCaptor<EventsViewModel.Command.ShowEmptyListMessage>()
        verify(commandMock, times(1)).postValue(commandCaptor.capture())
    }

    @Test
    fun `Get events, when it is requested to get events, then show exception error message`() = runBlocking {
        // ARRANGE
        val expectedError = "Exception message"
        val expectedViewState = EventsViewModel.ViewState()
        viewModel.viewState.observeForever(viewStateObserver)

        val expectedCommand = EventsViewModel.Command.ShowExceptionMessage(expectedError)

        whenever(repositoryMock.getEvents()).then {
            throw Exception(expectedError)
        }
        // ACT
        viewModel.getEvents()

        // ASSERT
        verify(viewStateObserver, times(1)).onChanged(expectedViewState.copy(isLoading = false))
        verify(repositoryMock, times(1)).getEvents()

        val commandCaptor = argumentCaptor<EventsViewModel.Command.ShowExceptionMessage>()
        verify(commandMock, times(1)).postValue(commandCaptor.capture())

        Assert.assertEquals(expectedCommand.message, expectedError)
    }

    @Test
    fun `Get events, when it is requested to get events, then show api error message`() = runBlocking {
        // ARRANGE
        val expectedViewState = EventsViewModel.ViewState()
        viewModel.viewState.observeForever(viewStateObserver)

        val expectedResponseError = ApiResponse("404")
        val expectedResponseBody: ResponseBody = ResponseBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            Gson().toJson(expectedResponseError)
        )

        val expectedCommand = EventsViewModel.Command.ShowApiErrorMessage
        val expectedResponseEvents = Response.error<List<Event>>(404, expectedResponseBody)
        whenever(repositoryMock.getEvents()).thenReturn(expectedResponseEvents)

        // ACT
        viewModel.getEvents()

        // ASSERT
        verify(viewStateObserver, times(1)).onChanged(expectedViewState.copy(isLoading = false))
        verify(repositoryMock, times(1)).getEvents()

        verify(commandMock, times(1)).postValue(expectedCommand)
    }

    @Test
    fun `Check connection network, when it is requested to check connection network, then show no connection message`() {
        // ARRANGE
        val expectedCommand = EventsViewModel.Command.ShowNoConnectionMessage
        val expectedViewState = EventsViewModel.ViewState()
        viewModel.viewState.observeForever(viewStateObserver)

        // ACT
        viewModel.showNoConnectionNetwork()

        // ASSERT
        verify(viewStateObserver, times(1)).onChanged(expectedViewState.copy(isLoading = false))

        verify(commandMock, times(1)).value = expectedCommand
    }
}