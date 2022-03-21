package br.com.giovannicampos.events.domain

data class Coupon(
    val id: String = "",
    val eventId: String = "",
    val discount: Int = 0
)