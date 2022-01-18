package br.com.giovannicampos.events.framework.mappers

import br.com.giovannicampos.events.domain.entities.Coupon
import br.com.giovannicampos.events.domain.entities.Event
import br.com.giovannicampos.events.domain.entities.Person
import br.com.giovannicampos.events.framework.models.EventModel

object EventMapper {

    fun toModel(entity: Event) = EventModel(
        id = entity.id,
        description = entity.description,
        date = entity.date,
        title = entity.title,
        image = entity.image,
        price = entity.price,
        latitude = entity.latitude,
        longitude = entity.longitude,
        listPersons = entity.listPersons.map {
            EventModel.PersonModel(
                it.id,
                it.eventId,
                it.name,
                it.email,
                it.picture
            )
        },
        listCoupons = entity.listCoupons.map {
            EventModel.CouponModel(
                it.id,
                it.eventId,
                it.discount
            )
        }
    )

    fun toEntity(model: EventModel) = Event(
        id = model.id,
        description = model.description,
        date = model.date,
        title = model.title,
        image = model.image,
        price = model.price,
        latitude = model.latitude,
        longitude = model.longitude,
        listPersons = model.listPersons.map {
            Person(
                it.id,
                it.eventId,
                it.name,
                it.email,
                it.picture
            )
        },
        listCoupons = model.listCoupons.map {
            Coupon(
                it.id,
                it.eventId,
                it.discount
            )
        }
    )
}