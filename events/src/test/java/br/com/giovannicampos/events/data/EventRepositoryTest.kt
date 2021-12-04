package br.com.giovannicampos.events.data

import br.com.giovannicampos.events.EventsTestUtils
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class EventRepositoryTest {

    private lateinit var dataSourceMock: EventDataSource
    private lateinit var repository: EventRepository

    private fun makeUnit(): Unit? = null

    @Before
    fun setUp() {
        dataSourceMock = mock()
        repository = EventRepository(dataSourceMock)
    }

    @Test
    fun fetchAllEvents() = runBlockingTest {
        val expected = EventsTestUtils.getEventsList()

        whenever(dataSourceMock.getEvents()).thenReturn(expected)

        val actual = repository.fetchAllEvents()

        assertEquals(expected, actual)
    }

    @Test
    fun getEventDetails() = runBlockingTest {
        val id = "1"
        val expected = EventsTestUtils.getEvent()

        whenever(dataSourceMock.getEventById(id)).thenReturn(expected)

        val actual = repository.getEventDetails(id)

        assertEquals(expected, actual)
    }

    @Test
    fun doCheckIn() = runBlockingTest {
        val person = EventsTestUtils.getPerson()

        whenever(dataSourceMock.postCheckIn(person)).thenReturn(makeUnit())

        repository.doCheckIn(person)

        verify(dataSourceMock, times(1)).postCheckIn(person)
    }
}