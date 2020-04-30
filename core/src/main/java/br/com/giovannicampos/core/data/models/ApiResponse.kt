package br.com.giovannicampos.core.data.models

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("code") val statusCode: String
)