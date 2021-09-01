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
class GetEventDetailsTest {

    private lateinit var repositoryMock: EventRepositoryContract
    private lateinit var interactor: GetEventDetails

    @Before
    fun setUp() {
        repositoryMock = mock()
        interactor = GetEventDetails(repositoryMock)
    }

    @Test
    fun getEventDetails() = runBlockingTest {
        val id = "1"
        val expected = EventsTestUtils.getEvent()

        whenever(repositoryMock.getEventDetails(id)).thenReturn(expected)

        val actual = interactor.invoke(id)

        assertEquals(expected, actual)
    }
}