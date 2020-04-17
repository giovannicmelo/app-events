package br.com.giovannicampos.appevents.events.ui.viewmodels

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.giovannicampos.appevents.base.command.models.GenericCommand
import br.com.giovannicampos.appevents.base.command.providers.CommandProvider
import br.com.giovannicampos.appevents.base.ui.SingleLiveEvent
import br.com.giovannicampos.appevents.events.data.contracts.EventsRepository
import br.com.giovannicampos.appevents.events.data.models.Event
import kotlinx.coroutines.launch

class EventDetailsViewModel(
    commandProvider: CommandProvider,
    private val repository: EventsRepository
) : ViewModel() {

    val viewState: MutableLiveData<ViewState> = MutableLiveData()
    val command: SingleLiveEvent<GenericCommand> = commandProvider.getCommand()

    init {
        viewState.value = ViewState()
    }

    fun getEventById(eventId: String) {
        viewModelScope.launch {
            try {
                val response = repository.getEventById(eventId)

                if (response.isSuccessful) {
                    response.body()?.let { event ->
                        command.postValue(Command.ShowEventDetails(event))
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

    fun openShareContentEvent() {
        command.value = Command.ShareContentEvent
    }

    fun openCheckInModal() {
        command.value = Command.OpenCheckInFormModal
    }

    fun validateFields(nameTyped: String, emailTyped: String) {
        if (nameTyped.isEmpty()) {
            command.value = Command.InvalidateName
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailTyped).matches()) {
            command.value = Command.InvalidateEmail
        } else {
            command.value = Command.ValidateFields
        }
    }

    fun doCheckIn(eventId: String, nameTyped: String, emailTyped: String) {
        viewModelScope.launch {
            try {
                val person = Event.Person(
                    eventId = eventId,
                    name = nameTyped,
                    email = emailTyped
                )
                val response = repository.postCheckIn(person)

                if (response.isSuccessful) {
                    command.postValue(Command.CheckInAccomplished)
                } else {
                    command.postValue(Command.ShowApiErrorMessage)
                }
            } catch (e: Exception) {
                command.postValue(Command.ShowExceptionMessage(e.localizedMessage!!))
            }
        }
    }

    private fun currentViewState(): ViewState = viewState.value!!

    sealed class Command : GenericCommand() {
        class ShowEventDetails(val event: Event) : Command()
        class ShowExceptionMessage(val message: String) : Command()
        object ShowApiErrorMessage : Command()
        object ShowNoConnectionMessage : Command()
        object ShareContentEvent : Command()
        object OpenCheckInFormModal : Command()
        object CheckInAccomplished : Command()
        object InvalidateName : Command()
        object InvalidateEmail : Command()
        object ValidateFields : Command()
    }

    data class ViewState(val isLoading: Boolean = true)
}