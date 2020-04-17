package br.com.giovannicampos.appevents.base.data.models

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("code") val statusCode: String
)