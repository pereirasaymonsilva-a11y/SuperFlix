package com.superflix.app.ui.screens.library

import androidx.lifecycle.ViewModel
import com.superflix.app.data.models.FavoriteItem
import com.superflix.app.data.models.HistoryItem
import com.superflix.app.data.models.MediaType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LibraryViewModel : ViewModel() {
    
    private val _favorites = MutableStateFlow<List<FavoriteItem>>(emptyList())
    val favorites: StateFlow<List<FavoriteItem>> = _favorites
    
    private val _history = MutableStateFlow<List<HistoryItem>>(emptyList())
    val history: StateFlow<List<HistoryItem>> = _history
    
    init {
        // Dados mockados para teste
        _favorites.value = listOf(
            FavoriteItem("tt0068646", MediaType.MOVIE, "O Poderoso Chefão"),
            FavoriteItem("tt1375666", MediaType.MOVIE, "Inception")
        )
        
        _history.value = listOf(
            HistoryItem("tt0133093", MediaType.MOVIE, "Matrix"),
            HistoryItem("tt0468569", MediaType.MOVIE, "The Dark Knight")
        )
    }
}