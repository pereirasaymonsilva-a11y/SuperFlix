// ui/screens/search/SearchViewModel.kt
package com.superflix.app.ui.screens.search

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.superflix.app.data.repository.MediaRepository
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: MediaRepository) : ViewModel() {
    private val _searchResults = mutableStateOf<List<String>>(emptyList())
    val searchResults = _searchResults
    
    private val _isLoading = mutableStateOf(false)
    val isLoading = _isLoading
    
    fun search(query: String) {
        if (query.length < 2) return
        
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val results = repository.search(query)
                _searchResults.value = results.take(50)
            } catch (e: Exception) {
                e.printStackTrace()
                _searchResults.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}

class SearchViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(MediaRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}