package br.com.giovannicampos.events.framework.models

import com.google.gson.annotations.SerializedName

data class CouponModel(
    @SerializedName("id") val id: String = "",
    @SerializedName("eventId") val eventId: String = "",
    @SerializedName("discount") val discount: Int = 0
){
    companion object{
        fun mock() = CouponModel(
            id = "2121",
            eventId = "31",
            discount = 30
        )
    }
}