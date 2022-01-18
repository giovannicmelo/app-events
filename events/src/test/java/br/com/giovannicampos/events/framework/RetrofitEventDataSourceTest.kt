package br.com.giovannicampos.events.framework

import br.com.giovannicampos.events.EventsTestUtils
import br.com.giovannicampos.events.domain.data.EventDataSource
import br.com.giovannicampos.events.framework.api.EventsApi
import br.com.giovannicampos.events.framework.mappers.EventMapper
import br.com.giovannicampos.events.framework.mappers.PersonMapper
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class RetrofitEventDataSourceTest {

    private lateinit var apiMock: EventsApi
    private lateinit var dataSource: EventDataSource

    private fun makeVoid(): Void? = null

    @Before
    fun setUp() {
        apiMock = mock()
        dataSource = RetrofitEventDataSource(apiMock)
    }

    @Test
    fun getEvents() = runBlockingTest {
        val expected = EventsTestUtils.getEventsList().map { EventMapper.toModel(it) }
        val response = Response.success(expected)
        whenever(apiMock.getEvents()).thenReturn(response)

        val actual = dataSource.getEvents().map { EventMapper.toModel(it) }

        assertEquals(expected, actual)
    }

    @Test
    fun getEventById() = runBlockingTest {
        val id = "1"
        val expected = EventMapper.toModel(EventsTestUtils.getEvent())
        val response = Response.success(expected)
        whenever(apiMock.getEventById(id)).thenReturn(response)

        val actual = EventMapper.toModel(dataSource.getEventById(id))

        assertEquals(expected, actual)
    }

    @Test
    fun postCheckIn() = runBlockingTest {
        val person = PersonMapper.toModel(EventsTestUtils.getPerson())
        val expected = makeVoid()
        val response = Response.success(expected)
        whenever(apiMock.postCheckIn(person)).thenReturn(response)

        dataSource.postCheckIn(PersonMapper.toEntity(person))

        verify(apiMock, times(1)).postCheckIn(person)
    }
}