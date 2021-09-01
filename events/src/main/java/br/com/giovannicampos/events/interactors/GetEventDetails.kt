package br.com.giovannicampos.events.interactors

import br.com.giovannicampos.events.data.EventRepositoryContract

class GetEventDetails(private val repository: EventRepositoryContract) : UseCase() {

    suspend operator fun invoke(eventId: String) = repository.getEventDetails(eventId)
}