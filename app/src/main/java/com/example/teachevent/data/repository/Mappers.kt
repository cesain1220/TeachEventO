//creado por CESAR ULISES BARILLA UMANA
package com.example.techevent.data.repository

import com.example.teachevent.data.local.EventEntity
import com.example.teachevent.domain.model.AgendaItem
import com.example.techevent.data.remote.EventDto
import com.example.techevent.data.remote.SpeakerDto
import com.example.techevent.data.remote.AgendaItemDto
import com.example.techevent.domain.model.Event
import com.example.techevent.domain.model.Speaker



fun EventDto.toDomain(isFavorite: Boolean = false): Event {
    return Event(
        id = this.id,
        title = this.title,
        description = this.description,
        date = this.date,
        location = this.location,
        imageUrl = this.imageUrl,
        hasAvailableSlots = this.hasAvailableSlots,
        isFavorite = isFavorite,
        speakers = this.speakers.map { it.toDomain() },
        agenda = this.agenda.map { it.toDomain() }
    )
}

fun SpeakerDto.toDomain() = Speaker(
    name = this.name,
    role = this.role,
    company = this.company
)

fun AgendaItemDto.toDomain() = AgendaItem(
    time = this.time,
    title = this.title
)


fun EventEntity.toDomain(): Event {
    return Event(
        id = this.id,
        title = this.title,
        description = this.description,
        date = this.date,
        location = this.location,
        imageUrl = this.imageUrl,
        hasAvailableSlots = this.status == "Cupos Disponibles",
        isFavorite = true,
        speakers = emptyList(),
        agenda = emptyList()
    )
}


fun Event.toEntity(): EventEntity {
    return EventEntity(
        id = this.id,
        title = this.title,
        date = this.date,
        location = this.location,
        status = if (this.hasAvailableSlots) "Cupos Disponibles" else "Agotado",
        imageUrl = this.imageUrl,
        description = this.description
    )
}