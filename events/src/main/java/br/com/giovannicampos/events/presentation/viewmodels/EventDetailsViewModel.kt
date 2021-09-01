package br.com.giovannicampos.events.presentation.viewmodels

import br.com.giovannicampos.core.ui.BaseViewModel
import br.com.giovannicampos.events.domain.Person
import br.com.giovannicampos.events.framework.utils.request
import br.com.giovannicampos.events.interactors.DoCheckIn
import br.com.giovannicampos.events.interactors.GetEventDetails

class EventDetailsViewModel(
    private val getEventDetails: GetEventDetails,
    private val doCheckIn: DoCheckIn
) : BaseViewModel() {

    fun getDetails(eventId: String) = request {
        getEventDetails(eventId)
    }

    fun doCheckIn(eventId: String, name: String, email: String) = request {
        val person = Person(
            name = name,
            eventId = eventId,
            email = email
        )
        doCheckIn(person)
    }
}