package br.com.giovannicampos.events.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.giovannicampos.core.utils.getOrAwaitValue
import br.com.giovannicampos.events.CoroutinesTestRule
import br.com.giovannicampos.events.EventsTestUtils
import br.com.giovannicampos.events.domain.entities.Event
import br.com.giovannicampos.events.framework.utils.State
import br.com.giovannicampos.events.domain.usecases.DoCheckIn
import br.com.giovannicampos.events.domain.usecases.GetEventDetails
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class EventDetailsViewModelTest {

    private lateinit var getEventDetailsMock: GetEventDetails
    private lateinit var doCheckInMock: DoCheckIn
    private lateinit var viewModel: EventDetailsViewModel

    @JvmField @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private fun makeUnit(): Unit? = null

    @Before
    fun setUp() {
        getEventDetailsMock = mock(GetEventDetails::class.java)
        doCheckInMock = mock(DoCheckIn::class.java)
        viewModel = EventDetailsViewModel(getEventDetailsMock, doCheckInMock)
    }

    @Test
    fun getDetails() = runBlockingTest {
        val id = "1"
        val event = EventsTestUtils.getEvent()
        val expectedDataState = State.DataState(event)
        val observerMock: Observer<State<Event>> = mock()
        whenever(getEventDetailsMock.invoke(id)).thenReturn(event)

        val actual = viewModel.getDetails(id)
        actual.observeForever(observerMock)

        verify(getEventDetailsMock, times(1)).invoke(id)
        verify(observerMock, times(1)).onChanged(expectedDataState)
        assertEquals(expectedDataState, actual.getOrAwaitValue())
    }

    @Test
    fun doCheckIn() = runBlockingTest {
        val person = EventsTestUtils.getPerson()
        val eventId = "1"
        val name = person.name
        val email = person.email
        val expectedDataState = State.DataState(Unit)
        val observerMock: Observer<State<Unit>> = mock()
        whenever(doCheckInMock.invoke(person)).thenReturn(makeUnit())

        val actual = viewModel.doCheckIn(eventId, name, email)
        actual.observeForever(observerMock)

        verify(doCheckInMock, times(1)).invoke(person)
        verify(observerMock, times(1)).onChanged(expectedDataState)
        assertEquals(expectedDataState, actual.getOrAwaitValue())
    }
}