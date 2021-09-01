package br.com.giovannicampos.events.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.giovannicampos.core.utils.getOrAwaitValue
import br.com.giovannicampos.events.CoroutinesTestRule
import br.com.giovannicampos.events.EventsTestUtils
import br.com.giovannicampos.events.domain.Event
import br.com.giovannicampos.events.framework.utils.State
import br.com.giovannicampos.events.interactors.FetchAllEvents
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
class EventsListViewModelTest {

    private lateinit var fetchAllEventsMock: FetchAllEvents
    private lateinit var viewModel: EventsListViewModel

    @JvmField @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setUp() {
        fetchAllEventsMock = mock(FetchAllEvents::class.java)
        viewModel = EventsListViewModel(fetchAllEventsMock)
    }

    @Test
    fun getEventsList() = runBlockingTest {
        val events = EventsTestUtils.getEventsList()
        val expectedDataState = State.DataState(events)
        val observerMock: Observer<State<List<Event>>> = mock()
        whenever(fetchAllEventsMock.invoke()).thenReturn(events)

        val actual = viewModel.getEventsList()
        actual.observeForever(observerMock)

        verify(fetchAllEventsMock, times(1)).invoke()
        verify(observerMock, times(1)).onChanged(expectedDataState)
        assertEquals(expectedDataState, actual.getOrAwaitValue())
    }
}