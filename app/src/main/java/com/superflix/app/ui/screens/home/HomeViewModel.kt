// ui/screens/home/HomeViewModel.kt
package com.superflix.app.ui.screens.home

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.superflix.app.data.repository.MediaRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MediaRepository) : ViewModel() {
    private val _movieIds = mutableStateOf<List<String>>(emptyList())
    val movieIds = _movieIds
    
    private val _seriesIds = mutableStateOf<List<String>>(emptyList())
    val seriesIds = _seriesIds
    
    private val _animeIds = mutableStateOf<List<String>>(emptyList())
    val animeIds = _animeIds
    
    private val _isLoading = mutableStateOf(true)
    val isLoading = _isLoading
    
    init {
        loadData()
    }
    
    private fun loadData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _movieIds.value = repository.getMovieIds(limit = 50)
                _seriesIds.value = repository.getSeriesIds(limit = 30)
                _animeIds.value = repository.getAnimeIds(limit = 30)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}

class HomeViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(MediaRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}