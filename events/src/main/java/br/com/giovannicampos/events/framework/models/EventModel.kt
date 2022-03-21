package br.com.giovannicampos.events.framework.models

import com.google.gson.annotations.SerializedName

data class EventModel(
    @SerializedName("id") val id: String = "",
    @SerializedName("title") val title: String = "",
    @SerializedName("price") val price: Double = 0.0,
    @SerializedName("latitude") val latitude: Double = 0.0,
    @SerializedName("longitude") val longitude: Double = 0.0,
    @SerializedName("image") val image: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("date") val date: Long = 0,
    @SerializedName("people") val listPersons: List<PersonModel> = listOf(),
    @SerializedName("coupons") val listCoupons: List<CouponModel> = listOf()
) {
    companion object {
        fun mock() = EventModel(
            id = "1",
            title = "São silvestre",
            price = 20.0,
            latitude = -23.556090,
            longitude = -46.537769,
            image = "https://upload.wikimedia.org/wikipedia/pt/c/c9/Favicon-ss.png",
            description = "Corrida de Rua",
            date = 100000,
            listPersons = listOf(
                PersonModel.mock(),
                PersonModel.mock().copy(
                    id = "2",
                    name = "Giovanni Campos",
                    email = "giovanni@abc.com.br"
                )
            ),
            listCoupons = listOf(
                CouponModel.mock()
            )
        )
    }
}