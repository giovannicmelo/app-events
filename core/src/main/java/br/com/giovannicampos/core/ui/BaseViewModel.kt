package br.com.giovannicampos.core.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    private val _snackMessage = MutableLiveData<String>()
    val snackMessage: LiveData<String> = _snackMessage

    fun showSnackMessage(message: String) {
        _snackMessage.value = message
    }
}