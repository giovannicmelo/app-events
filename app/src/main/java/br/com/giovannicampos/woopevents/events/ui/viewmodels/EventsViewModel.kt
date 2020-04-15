package br.com.giovannicampos.woopevents.events.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.giovannicampos.woopevents.base.command.models.GenericCommand
import br.com.giovannicampos.woopevents.base.command.providers.CommandProvider
import br.com.giovannicampos.woopevents.base.ui.SingleLiveEvent
import br.com.giovannicampos.woopevents.events.data.contracts.EventsRepository
import br.com.giovannicampos.woopevents.events.data.models.Event
import kotlinx.coroutines.launch

class EventsViewModel(commandProvider: CommandProvider, private val repository: EventsRepository) :
    ViewModel() {

    val viewState: MutableLiveData<ViewState> = MutableLiveData()
    val command: SingleLiveEvent<GenericCommand> = commandProvider.getCommand()

    init {
        viewState.value = ViewState()
    }

    fun getEvents() {
        viewModelScope.launch {
            try {
                val response = repository.getEvents()

                if (response.isSuccessful) {
                    response.body()?.let { events ->
                        if (events.isNotEmpty()) {
                            command.postValue(Command.ShowEvents(events))
                        } else {
                            command.postValue(Command.ShowEmptyListMessage)
                        }
                    }
                } else {
                    response.errorBody()?.let {
                        command.postValue(Command.ShowApiErrorMessage)
                    }
                }

                viewState.postValue(currentViewState().copy(isLoading = false))
            } catch (e: Exception) {
                command.postValue(Command.ShowExceptionMessage(e.localizedMessage!!))
                viewState.postValue(currentViewState().copy(isLoading = false))
            }
        }
    }

    fun showNoConnectionNetwork() {
        viewState.value = currentViewState().copy(isLoading = false)
        command.value = Command.ShowNoConnectionMessage
    }

    private fun currentViewState(): ViewState = viewState.value!!

    sealed class Command : GenericCommand() {
        class ShowEvents(val events: List<Event>) : Command()
        class ShowExceptionMessage(val message: String) : Command()
        object ShowApiErrorMessage : Command()
        object ShowEmptyListMessage : Command()
        object ShowNoConnectionMessage : Command()
    }

    data class ViewState(val isLoading: Boolean = true)
}