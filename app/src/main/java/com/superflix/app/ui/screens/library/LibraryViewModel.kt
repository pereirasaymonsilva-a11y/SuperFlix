package com.superflix.app.ui.screens.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superflix.app.data.database.AppDatabase
import com.superflix.app.data.database.FavoriteEntity
import com.superflix.app.data.database.HistoryEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LibraryViewModel(
    private val database: AppDatabase
) : ViewModel() {
    
    private val _favorites = MutableStateFlow<List<FavoriteEntity>>(emptyList())
    val favorites: StateFlow<List<FavoriteEntity>> = _favorites
    
    private val _history = MutableStateFlow<List<HistoryEntity>>(emptyList())
    val history: StateFlow<List<HistoryEntity>> = _history
    
    init {
        loadFavorites()
        loadHistory()
    }
    
    fun loadFavorites() {
        viewModelScope.launch {
            val favs = database.favoriteDao().getAllFavorites()
            _favorites.value = favs
        }
    }
    
    fun loadHistory() {
        viewModelScope.launch {
            val hist = database.historyDao().getHistory()
            _history.value = hist
        }
    }
}