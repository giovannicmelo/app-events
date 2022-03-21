package br.com.giovannicampos.events.framework.utils

import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ExceptionHandler {

    companion object {
        fun resolveError(e: Exception): State.ErrorState {
            var error = e
            when (e) {
                is SocketTimeoutException -> {
                    error = NetworkErrorException(errorMessage = "Connection error")
                }
                is ConnectException -> {
                    error = NetworkErrorException(errorMessage = "No internet connection error")
                }
                is UnknownHostException -> {
                    error = NetworkErrorException(errorMessage = "Unknown error")
                }
                is HttpException -> {
                    when (e.code()) {
                        502 -> {
                            error = NetworkErrorException(e.code(), "Internal error")
                        }
                        400, 404 -> {
                            error = NetworkErrorException.parseException(e)
                        }
                    }
                }
                else -> {
                    error = NetworkErrorException(errorMessage = e.localizedMessage!!)
                }
            }

            return State.ErrorState(error)
        }
    }
}