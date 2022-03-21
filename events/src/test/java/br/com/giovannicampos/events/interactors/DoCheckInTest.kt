package br.com.giovannicampos.events.interactors

import br.com.giovannicampos.events.EventsTestUtils
import br.com.giovannicampos.events.data.EventRepositoryContract
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DoCheckInTest {

    private lateinit var repositoryMock: EventRepositoryContract
    private lateinit var interactor: DoCheckIn

    private fun makeUnit(): Unit? = null

    @Before
    fun setUp() {
        repositoryMock = mock()
        interactor = DoCheckIn(repositoryMock)
    }

    @Test
    fun doCheckIn() = runBlockingTest {
        val person = EventsTestUtils.getPerson()

        whenever(repositoryMock.doCheckIn(person)).thenReturn(makeUnit())

        interactor.invoke(person)

        verify(repositoryMock, times(1)).doCheckIn(person)
    }
}