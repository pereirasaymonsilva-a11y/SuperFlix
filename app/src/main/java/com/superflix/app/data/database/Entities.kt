package com.superflix.app.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.superflix.app.data.models.MediaType

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val posterPath: String?,
    val type: MediaType,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val posterPath: String?,
    val type: MediaType,
    val progress: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)