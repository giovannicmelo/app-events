package br.com.giovannicampos.events.mappers

import br.com.giovannicampos.events.domain.Person
import br.com.giovannicampos.events.framework.models.EventModel
import br.com.giovannicampos.events.framework.models.PersonModel

object PersonMapper {

    fun toModel(entity: Person) = PersonModel(
        entity.id,
        entity.eventId,
        entity.name,
        entity.email,
        entity.picture
    )

    fun toEntity(model: PersonModel) = Person(
        model.id,
        model.eventId,
        model.name,
        model.email,
        model.picture
    )
}