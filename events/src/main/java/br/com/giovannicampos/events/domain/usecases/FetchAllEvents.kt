package br.com.giovannicampos.events.domain.usecases

import br.com.giovannicampos.events.domain.data.EventRepositoryContract

class FetchAllEvents(private val repository: EventRepositoryContract) : UseCase() {

    suspend operator fun invoke() = repository.fetchAllEvents()
}