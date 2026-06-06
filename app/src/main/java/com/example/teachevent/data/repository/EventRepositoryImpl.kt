//creado por CESAR ULISES BARILLA UMANA
package com.example.teachevent.data.repository

import android.util.Log
import com.example.teachevent.data.local.EventDao
import com.example.teachevent.data.local.EventEntity
import com.example.teachevent.data.remote.ApiService
import com.example.teachevent.domain.model.AgendaItem
import com.example.teachevent.domain.repository.EventRepository
import com.example.techevent.domain.model.Event
import com.example.techevent.domain.model.Speaker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class EventRepositoryImpl(
    private val apiService: ApiService,
    private val eventDao: EventDao
) : EventRepository {

    override fun getFavoriteIds(): Flow<Set<String>> {
        return eventDao.getFavoriteEvents().map { entities ->
            entities.map { it.id }.toSet()
        }
    }

    override suspend fun getEvents(): Pair<List<Event>, Boolean> = withContext(Dispatchers.IO) {
        try {
            val favoriteIds = eventDao.getFavoriteIdsDirect().toSet()

            val remoteDtos = apiService.fetchEvents()

            val events = remoteDtos.map { dto ->
                val hasSlots = dto.hasAvailableSlots
                Event(
                    id = dto.id,
                    title = dto.title,
                    description = dto.description,
                    date = dto.date,
                    location = dto.location,
                    imageUrl = dto.imageUrl,
                    hasAvailableSlots = hasSlots,
                    isFavorite = favoriteIds.contains(dto.id),
                    speakers = dto.speakers.map { Speaker(it.name, it.role, it.company) },
                    agenda = dto.agenda.map { AgendaItem(it.time, it.title) }
                )
            }
            Pair(events, false)
        } catch (e: Exception) {
            Log.e("REPOSITORY_ERROR", "Fallo remoto, activando respaldo local Room: ${e.message}")

            val cachedEntities = eventDao.getFavoriteEventsDirect()
            val cachedEvents = cachedEntities.map { entity ->
                Event(
                    id = entity.id,
                    title = entity.title,
                    description = entity.description,
                    date = entity.date,
                    location = entity.location,
                    imageUrl = entity.imageUrl,
                    hasAvailableSlots = entity.status == "Cupos Disponibles",
                    isFavorite = true,
                    speakers = emptyList(),
                    agenda = emptyList()
                )
            }

            if (cachedEvents.isEmpty()) {
                throw Exception("Sin conexión a internet y sin eventos locales guardados.")
            }
            Pair(cachedEvents, true)
        }
    }

    override suspend fun toggleFavorite(event: Event, isFavorite: Boolean) = withContext(Dispatchers.IO) {
        val entity = EventEntity(
            id = event.id,
            title = event.title,
            description = event.description,
            date = event.date,
            location = event.location,
            imageUrl = event.imageUrl,
            status = if (event.hasAvailableSlots) "Cupos Disponibles" else "Agotado"
        )

        if (isFavorite) {
            eventDao.insertFavorite(entity)
        } else {
            eventDao.deleteFavorite(entity)
        }
    }
}