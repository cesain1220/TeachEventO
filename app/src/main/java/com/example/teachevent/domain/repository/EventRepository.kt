//creado por CESAR ULISES BARILLA UMANA
package com.example.teachevent.domain.repository

import com.example.techevent.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getFavoriteIds(): Flow<Set<String>>
    suspend fun getEvents(): Pair<List<Event>, Boolean>
    suspend fun toggleFavorite(event: Event, isFavorite: Boolean)
}