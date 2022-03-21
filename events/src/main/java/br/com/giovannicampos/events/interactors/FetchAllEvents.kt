package br.com.giovannicampos.events.interactors

import br.com.giovannicampos.events.data.EventRepositoryContract

class FetchAllEvents(private val repository: EventRepositoryContract) : UseCase() {

    suspend operator fun invoke() = repository.fetchAllEvents()
}