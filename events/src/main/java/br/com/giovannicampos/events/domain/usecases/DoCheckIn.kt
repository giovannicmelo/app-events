package br.com.giovannicampos.events.domain.usecases

import br.com.giovannicampos.events.domain.data.EventRepositoryContract
import br.com.giovannicampos.events.domain.entities.Person

class DoCheckIn(private val repository: EventRepositoryContract) : UseCase() {

    suspend operator fun invoke(person: Person) = repository.doCheckIn(person)
}