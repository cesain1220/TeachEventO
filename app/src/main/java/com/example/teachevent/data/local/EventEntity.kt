//creado por CESAR ULISES BARILLA UMANA
package com.example.teachevent.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "events_table")
data class EventEntity(
    @PrimaryKey val id: String,
    val title: String,
    val date: String,
    val location: String,
    val status: String,
    val imageUrl: String,
    val description: String
)