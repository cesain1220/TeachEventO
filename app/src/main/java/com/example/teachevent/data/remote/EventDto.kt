//creado por CESAR ULISES BARILLA UMANA
package com.example.techevent.data.remote

import androidx.room.Query
import kotlinx.serialization.Serializable

@Serializable

data class EventDto(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val location: String,
    val imageUrl: String,
    val hasAvailableSlots: Boolean,
    val speakers: List<SpeakerDto> = emptyList(),
    val agenda: List<AgendaItemDto> = emptyList()
)

@Serializable

data class SpeakerDto(
    val name: String,
    val role: String,
    val company: String
)



@Serializable
data class AgendaItemDto(
    val time: String,
    val title: String
)