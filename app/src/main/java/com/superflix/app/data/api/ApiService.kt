package com.superflix.app.data.api

import com.superflix.app.data.models.Genre
import com.superflix.app.data.models.Movie
import com.superflix.app.data.models.MovieDetails
import com.superflix.app.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.URL

class ApiService {
    
    // Busca IDs de filmes
    suspend fun getMovieIds(category: String = "filme"): List<String> {
        return withContext(Dispatchers.IO) {
            try {
                val url = "${Constants.BASE_API_URL}${Constants.ENDPOINT_LISTA}?category=$category&format=json"
                val json = URL(url).readText()
                val array = JSONArray(json)
                val ids = mutableListOf<String>()
                for (i in 0 until array.length()) {
                    ids.add(array.getString(i))
                }
                ids
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
    
    // Busca detalhes do filme
    suspend fun getMovieDetails(movieId: String): MovieDetails? {
        return withContext(Dispatchers.IO) {
            try {
                // Dados mockados por enquanto - a API não retorna JSON detalhado
                MovieDetails(
                    id = movieId,
                    title = "Filme $movieId",
                    overview = "Sinopse do filme $movieId",
                    voteAverage = 7.5,
                    year = "2024"
                )
            } catch (e: Exception) {
                null
            }
        }
    }
    
    // Busca por texto
    suspend fun search(query: String): List<String> {
        return withContext(Dispatchers.IO) {
            try {
                val url = "${Constants.BASE_API_URL}${Constants.ENDPOINT_LISTA}?category=pesquisa&q=$query&format=json"
                val json = URL(url).readText()
                val array = JSONArray(json)
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
}