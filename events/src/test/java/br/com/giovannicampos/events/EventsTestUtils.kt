package br.com.giovannicampos.events

import br.com.giovannicampos.events.domain.entities.Coupon
import br.com.giovannicampos.events.domain.entities.Event
import br.com.giovannicampos.events.domain.entities.Person

object EventsTestUtils {

    private fun getCoupon() = Coupon(
        id = "1",
        eventId = "1",
        discount = 10
    )

    fun getPerson() = Person(
        name = "Test Person",
        email = "person@email.com",
        eventId = "1"
    )

    fun getEvent() = Event(
        id = "1",
        date = 1534784400000,
        title = "Event 1",
        description = "Test event 1",
        price = 10.00,
        latitude = -51.2146267,
        longitude = -30.0392981,
        listCoupons = listOf(getCoupon()),
        listPersons = listOf(getPerson())
    )

    fun getEventsList() = listOf(getEvent())
}