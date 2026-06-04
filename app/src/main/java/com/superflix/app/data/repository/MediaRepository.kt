// data/repository/MediaRepository.kt
package com.superflix.app.data.repository

import android.content.Context
import com.superflix.app.data.api.RetrofitInstance
import com.superflix.app.data.database.AppDatabase
import com.superflix.app.data.database.FavoriteEntity
import com.superflix.app.data.database.HistoryEntity
import com.superflix.app.data.models.MediaItem
import com.superflix.app.data.models.MediaType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class MediaRepository(private val context: Context) {
    private val api = RetrofitInstance.api
    private val database = AppDatabase.getDatabase(context)
    
    suspend fun getMovieIds(limit: Int = 50): List<String> = withContext(Dispatchers.IO) {
        try {
            api.getMovieIds(limit = limit)
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    suspend fun getSeriesIds(limit: Int = 50): List<String> = withContext(Dispatchers.IO) {
        try {
            api.getSeriesIds(limit = limit)
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    suspend fun getAnimeIds(limit: Int = 50): List<String> = withContext(Dispatchers.IO) {
        try {
            api.getAnimeIds(limit = limit)
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    suspend fun search(query: String): List<String> = withContext(Dispatchers.IO) {
        try {
            api.search(query = query)
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    suspend fun getFavorites(): List<FavoriteEntity> = withContext(Dispatchers.IO) {
        database.favoriteDao().getAllFavorites()
    }
    
    suspend fun addToFavorites(item: MediaItem) = withContext(Dispatchers.IO) {
        database.favoriteDao().addFavorite(
            FavoriteEntity(
                id = item.id,
                title = item.title,
                posterPath = item.posterPath,
                type = item.type
            )
        )
    }
    
    suspend fun removeFromFavorites(id: String) = withContext(Dispatchers.IO) {
        database.favoriteDao().removeFavorite(
            FavoriteEntity(id, "", null, MediaType.MOVIE)
        )
    }
    
    suspend fun isFavorite(id: String): Boolean = withContext(Dispatchers.IO) {
        database.favoriteDao().isFavorite(id)
    }
    
    suspend fun addToHistory(item: MediaItem, progress: Int = 0) = withContext(Dispatchers.IO) {
        database.historyDao().addToHistory(
            HistoryEntity(
                id = item.id,
                title = item.title,
                posterPath = item.posterPath,
                type = item.type,
                progress = progress
            )
        )
    }
    
    suspend fun getHistory(): List<HistoryEntity> = withContext(Dispatchers.IO) {
        database.historyDao().getHistory()
    }
    
    fun getContinueWatching(): Flow<List<HistoryEntity>> = flow {
        emit(database.historyDao().getHistory())
    }
}