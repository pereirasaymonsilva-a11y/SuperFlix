package com.superflix.app.ui.screens.home

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.superflix.app.data.repository.MediaRepository

class HomeViewModel(private val repository: MediaRepository) : ViewModel() {
    val movieIds = mutableStateOf<List<String>>(emptyList())
    val seriesIds = mutableStateOf<List<String>>(emptyList())
    val animeIds = mutableStateOf<List<String>>(emptyList())
    val isLoading = mutableStateOf(true)
    
    init {
        movieIds.value = listOf("550", "13", "155", "497", "19995", "120", "121", "122", "123", "124")
        seriesIds.value = listOf("1396", "1402", "1418")
        animeIds.value = listOf("21", "22", "23")
        isLoading.value = false
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