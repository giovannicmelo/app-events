package br.com.giovannicampos.core.data.network

import com.google.gson.annotations.SerializedName

class ErrorResponse {
    @SerializedName("status_code") var statusCode: Int? = null
    @SerializedName("message") var message: String? = null
}