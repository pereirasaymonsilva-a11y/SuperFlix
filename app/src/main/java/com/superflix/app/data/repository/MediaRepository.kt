// data/repository/MediaRepository.kt
package com.superflix.app.data.repository

import com.superflix.app.data.api.ApiService
import com.superflix.app.data.models.MediaType
import com.superflix.app.data.models.Movie
import javax.inject.Inject

class MediaRepository @Inject constructor(
    private val apiService: ApiService
) {
    
    suspend fun getMovieIds(limit: Int = 20): List<String> {
        return try {
            val ids = apiService.getMovieIds("filme")
            ids.take(limit)
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    suspend fun getSeriesIds(limit: Int = 20): List<String> {
        return try {
            val ids = apiService.getMovieIds("serie")
            ids.take(limit)
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    suspend fun getAnimeIds(limit: Int = 20): List<String> {
        return try {
            val ids = apiService.getMovieIds("anime")
            ids.take(limit)
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    suspend fun getMovieDetails(movieId: String): Movie? {
        return try {
            apiService.getMovieDetails(movieId)
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun search(query: String): List<String> {
        return try {
            // Implementar busca na API
            val url = "${com.superflix.app.utils.Constants.BASE_API_URL}${com.superflix.app.utils.Constants.ENDPOINT_LISTA}?category=pesquisa&q=$query&format=json"
            val json = java.net.URL(url).readText()
            val array = org.json.JSONArray(json)
            val results = mutableListOf<String>()
            for (i in 0 until array.length()) {
                results.add(array.getString(i))
            }
            results
        } catch (e: Exception) {
            emptyList()
        }
    }
}