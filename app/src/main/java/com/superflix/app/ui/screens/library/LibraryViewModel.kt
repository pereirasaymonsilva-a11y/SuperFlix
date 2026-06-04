// ui/screens/library/LibraryViewModel.kt
package com.superflix.app.ui.screens.library

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.superflix.app.data.database.FavoriteEntity
import com.superflix.app.data.database.HistoryEntity
import com.superflix.app.data.repository.MediaRepository
import kotlinx.coroutines.launch

class LibraryViewModel(private val repository: MediaRepository) : ViewModel() {
    private val _favorites = mutableStateOf<List<FavoriteEntity>>(emptyList())
    val favorites = _favorites
    
    private val _history = mutableStateOf<List<HistoryEntity>>(emptyList())
    val history = _history
    
    init {
        loadData()
    }
    
    private fun loadData() {
        viewModelScope.launch {
            _favorites.value = repository.getFavorites()
            _history.value = repository.getHistory()
        }
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