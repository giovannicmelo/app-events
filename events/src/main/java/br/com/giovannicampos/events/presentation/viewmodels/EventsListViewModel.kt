package br.com.giovannicampos.events.presentation.viewmodels

import br.com.giovannicampos.core.ui.BaseViewModel
import br.com.giovannicampos.events.framework.utils.request
import br.com.giovannicampos.events.domain.usecases.FetchAllEvents

class EventsListViewModel(private val fetchAllEvents: FetchAllEvents) : BaseViewModel() {

    fun getEventsList() = request {
        fetchAllEvents()
    }
}