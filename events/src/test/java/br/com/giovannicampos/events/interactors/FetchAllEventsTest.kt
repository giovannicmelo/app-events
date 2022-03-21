package br.com.giovannicampos.events.interactors

import br.com.giovannicampos.events.EventsTestUtils
import br.com.giovannicampos.events.data.EventRepositoryContract
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchAllEventsTest {

    private lateinit var repositoryMock: EventRepositoryContract
    private lateinit var interactor: FetchAllEvents

    @Before
    fun setUp() {
        repositoryMock = mock()
        interactor = FetchAllEvents(repositoryMock)
    }

    @Test
    fun fetchAll() = runBlockingTest {
        val expected = EventsTestUtils.getEventsList()

        whenever(repositoryMock.fetchAllEvents()).thenReturn(expected)

        val actual = interactor.invoke()

        assertEquals(expected, actual)
    }
}