package br.com.giovannicampos.events.domain

data class Event(
    val id: String = "",
    val title: String = "",
    val price: Double = 0.0,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val image: String = "",
    val description: String = "",
    val date: Long = 0,
    val listPersons: List<Person> = listOf(),
    val listCoupons: List<Coupon> = listOf()
)