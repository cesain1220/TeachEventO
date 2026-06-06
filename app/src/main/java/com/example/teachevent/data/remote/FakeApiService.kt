//creado por CESAR ULISES BARILLA UMANA
package com.example.teachevent.data.remote

import com.example.techevent.data.remote.EventDto
import com.example.techevent.data.remote.SpeakerDto
import com.example.techevent.data.remote.AgendaItemDto

class FakeApiService : ApiService {
    override suspend fun fetchEvents(): List<EventDto> {
        return listOf(
            EventDto(
                id = "1",
                title = "Android Dev Summit 2026",
                description = "Únete a las conferencias sobre Jetpack Compose, Kotlin Multiplatform y más novedades del ecosistema Android.",
                date = "15 de Junio, 2026",
                location = "Online / San Francisco",
                imageUrl = "https://images.unsplash.com/photo-1607799279861-4dd421887fb3?w=500",
                hasAvailableSlots = true,
                speakers = listOf(
                    SpeakerDto(name = "Manuel Salvador", role = "GDE Android", company = "TeachEvent"),
                    SpeakerDto(name = "Sofía Rossi", role = "Lead Designer", company = "DesignStudio")
                ),
                agenda = listOf(
                    AgendaItemDto(time = "09:00 AM", title = "Registro y Bienvenida"),
                    AgendaItemDto(time = "10:00 AM", title = "Keynote: El futuro de Compose")
                )
            ),
            EventDto(
                id = "2",
                title = "Kotlin Everywhere",
                description = "Un evento dedicado al 100% a explorar Kotlin en servidor, web y móvil.",
                date = "22 de Julio, 2026",
                location = "Auditorio Tech, Madrid",
                imageUrl = "https://images.unsplash.com/photo-1555066931-4365d14bab8c?w=500",
                hasAvailableSlots = false,
                speakers = listOf(
                    SpeakerDto(name = "Alejandro Gómez", role = "Kotlin Specialist", company = "JetBrains")
                ),
                agenda = listOf(
                    AgendaItemDto(time = "11:00 AM", title = "KMP en entornos de producción masivos")
                )
            )
        )
    }
}