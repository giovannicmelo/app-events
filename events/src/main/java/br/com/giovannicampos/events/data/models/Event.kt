package br.com.giovannicampos.events.data.models

import com.google.gson.annotations.SerializedName

data class Event(
    @SerializedName("id") val id: String = "",
    @SerializedName("title") val title: String = "",
    @SerializedName("price") val price: Double = 0.0,
    @SerializedName("latitude") val latitude: Double = 0.0,
    @SerializedName("longitude") val longitude: Double = 0.0,
    @SerializedName("image") val image: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("date") val date: Long = 0,
    @SerializedName("people") val listPersons: List<Person> = listOf(),
    @SerializedName("cupons") val listCoupons: List<Coupon> = listOf()
) {

    data class Coupon(
        @SerializedName("id") val id: String = "",
        @SerializedName("eventId") val eventId: String = "",
        @SerializedName("discount") val discount: Int = 0
    )

    data class Person(
        @SerializedName("id") val id: String = "",
        @SerializedName("eventId") val eventId: String = "",
        @SerializedName("name") val name: String = "",
        @SerializedName("email") val email: String = "",
        @SerializedName("picture") val picture: String = ""
    )
}