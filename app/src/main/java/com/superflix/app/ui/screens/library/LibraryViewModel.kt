package com.superflix.app.ui.screens.library

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.superflix.app.data.database.FavoriteEntity
import com.superflix.app.data.database.HistoryEntity
import com.superflix.app.data.models.MediaType
import com.superflix.app.data.repository.MediaRepository

class LibraryViewModel(private val repository: MediaRepository) : ViewModel() {
    val favorites = mutableStateOf<List<FavoriteEntity>>(emptyList())
    val history = mutableStateOf<List<HistoryEntity>>(emptyList())
    
    init {
        favorites.value = listOf(
            FavoriteEntity("550", "Fight Club", null, MediaType.MOVIE, System.currentTimeMillis()),
            FavoriteEntity("13", "Forrest Gump", null, MediaType.MOVIE, System.currentTimeMillis()),
            FavoriteEntity("497", "The Matrix", null, MediaType.MOVIE, System.currentTimeMillis())
        )
        history.value = listOf(
            HistoryEntity("550", "Fight Club", null, MediaType.MOVIE, 50, System.currentTimeMillis()),
            HistoryEntity("497", "The Matrix", null, MediaType.MOVIE, 30, System.currentTimeMillis()),
            HistoryEntity("19995", "Avatar", null, MediaType.MOVIE, 80, System.currentTimeMillis())
        )
    }
}

class LibraryViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LibraryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LibraryViewModel(MediaRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}