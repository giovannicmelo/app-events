package br.com.giovannicampos.events.framework.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import br.com.giovannicampos.events.framework.utils.State as NetworkState

fun <T> request(requestBlock: suspend () -> T): LiveData<NetworkState<T>> {
    return liveData {
        emit(NetworkState.LoadingState)
        try {
            emit(NetworkState.DataState(requestBlock()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ExceptionHandler.resolveError(e))
        }
    }
}