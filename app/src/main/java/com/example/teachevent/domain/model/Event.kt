//creado por CESAR ULISES BARILLA UMANA
package com.example.techevent.domain.model

import com.example.teachevent.domain.model.AgendaItem

data class Event(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val location: String,
    val imageUrl: String,
    val hasAvailableSlots: Boolean, // Cambiado a Boolean para que coincida con la UI y los DTOs
    val speakers: List<Speaker> = emptyList(),
    val agenda: List<AgendaItem> = emptyList(),
    val isFavorite: Boolean = false
)