package com.superflix.app.ui.screens.search

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.superflix.app.data.repository.MediaRepository

class SearchViewModel(private val repository: MediaRepository) : ViewModel() {
    val searchResults = mutableStateOf<List<String>>(emptyList())
    val isLoading = mutableStateOf(false)
    
    fun search(query: String) {
        isLoading.value = true
        if (query.isNotEmpty()) {
            searchResults.value = listOf("550", "13", "155", "497", "19995", "120", "121")
        } else {
            searchResults.value = emptyList()
        }
        isLoading.value = false
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