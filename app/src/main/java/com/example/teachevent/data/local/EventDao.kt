//creado por CESAR ULISES BARILLA UMANA
package com.example.teachevent.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Query("SELECT * FROM events_table")
    fun getFavoriteEvents(): Flow<List<EventEntity>>


    @Query("SELECT * FROM events_table")
    suspend fun getFavoriteEventsDirect(): List<EventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(event: EventEntity)

    @Query("SELECT id FROM events_table")
    suspend fun getFavoriteIdsDirect(): List<String>

    @Delete
    suspend fun deleteFavorite(event: EventEntity)

    @Query("SELECT EXISTS(SELECT * FROM events_table WHERE id = :id)")
    suspend fun isFavorite(id: String): Boolean
}