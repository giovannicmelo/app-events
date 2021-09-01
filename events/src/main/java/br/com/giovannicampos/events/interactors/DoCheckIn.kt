package br.com.giovannicampos.events.interactors

import br.com.giovannicampos.events.data.EventRepositoryContract
import br.com.giovannicampos.events.domain.Person

class DoCheckIn(private val repository: EventRepositoryContract) : UseCase() {

    suspend operator fun invoke(person: Person) = repository.doCheckIn(person)
}