// data/api/ApiService.kt
package com.superflix.app.data.api

import com.superflix.app.data.models.Movie
import com.superflix.app.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class ApiService {
    
    // Busca lista de IDs de filmes populares
    suspend fun getMovieIds(category: String = "movie"): List<String> {
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
                e.printStackTrace()
                emptyList()
            }
        }
    }
    
    // Busca detalhes de um filme pelo ID
    suspend fun getMovieDetails(movieId: String): Movie? {
        return withContext(Dispatchers.IO) {
            try {
                // Primeiro busca o HTML da página do filme
                val url = "${Constants.BASE_API_URL}${Constants.ENDPOINT_FILME}/$movieId"
                val html = URL(url).readText()
                
                // Extrai informações do HTML (você pode precisar ajustar)
                // Por enquanto, retorna dados básicos
                Movie(
                    id = movieId,
                    title = extractTitleFromHtml(html),
                    posterPath = extractPosterFromHtml(html),
                    overview = extractOverviewFromHtml(html)
                )
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
    
    // Busca detalhes de uma série
    suspend fun getSeriesDetails(seriesId: String): Movie? {
        return withContext(Dispatchers.IO) {
            try {
                val url = "${Constants.BASE_API_URL}${Constants.ENDPOINT_SERIE}/$seriesId"
                val html = URL(url).readText()
                
                Movie(
                    id = seriesId,
                    title = extractTitleFromHtml(html),
                    posterPath = extractPosterFromHtml(html)
                )
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
    
    // Métodos auxiliares para extrair dados do HTML
    private fun extractTitleFromHtml(html: String): String {
        // Implementar extração baseado na estrutura do HTML
        val regex = "<title>(.*?)</title>".toRegex()
        return regex.find(html)?.groupValues?.get(1)?.replace(" - SuperFlix", "") ?: "Título não disponível"
    }
    
    private fun extractPosterFromHtml(html: String): String? {
        // Implementar extração da URL do poster
        val regex = "poster_path\":\"(.*?)\"".toRegex()
        return regex.find(html)?.groupValues?.get(1)
    }
    
    private fun extractOverviewFromHtml(html: String): String? {
        // Implementar extração da sinopse
        val regex = "overview\":\"(.*?)\"".toRegex()
        return regex.find(html)?.groupValues?.get(1)
    }
}