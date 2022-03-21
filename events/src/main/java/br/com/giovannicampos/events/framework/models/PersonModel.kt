package br.com.giovannicampos.events.framework.models

import com.google.gson.annotations.SerializedName

data class PersonModel(
    @SerializedName("id") val id: String = "",
    @SerializedName("eventId") val eventId: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("picture") val picture: String = ""
) {
    companion object {
        fun mock() = PersonModel(
            id = "21",
            eventId = "32",
            name = "Rodrigo Gielow",
            email = "rodrigo@abc.com.br",
            picture = ""
        )
    }
}