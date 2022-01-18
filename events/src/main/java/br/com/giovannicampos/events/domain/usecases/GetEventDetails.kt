package br.com.giovannicampos.events.domain.usecases

import br.com.giovannicampos.events.domain.data.EventRepositoryContract

class GetEventDetails(private val repository: EventRepositoryContract) : UseCase() {

    suspend operator fun invoke(eventId: String) = repository.getEventDetails(eventId)
}