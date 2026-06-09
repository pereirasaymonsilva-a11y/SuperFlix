package com.superflix.app.data.api

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
                println("🔍 API URL: $url")  // Log para debug
                
                val json = URL(url).readText()
                println("📦 API Response: $json")  // Log para debug
                
                val array = JSONArray(json)
                val ids = mutableListOf<String>()
                for (i in 0 until array.length()) {
                    ids.add(array.getString(i))
                }
                println("✅ IDs encontrados: ${ids.size}")  // Log para debug
                ids
            } catch (e: Exception) {
                println("❌ Erro na API: ${e.message}")  // Log para debug
                // Dados mockados para teste enquanto API não responde
                listOf(
                    "tt0068646",  // O Poderoso Chefão
                    "tt1375666",  // Inception
                    "tt0133093",  // Matrix
                    "tt0468569",  // The Dark Knight
                    "tt0944947"   // Game of Thrones
                )
            }
        }
    }
    
    // Busca detalhes do filme
    suspend fun getMovieDetails(movieId: String): MovieDetails? {
        return withContext(Dispatchers.IO) {
            try {
                // Tenta buscar da API primeiro
                val url = "${Constants.BASE_API_URL}${Constants.ENDPOINT_FILME}/$movieId?format=json"
                println("🔍 Details URL: $url")
                
                val html = URL(url).readText()
                println("📦 Details Response (primeiros 500 chars): ${html.take(500)}")
                
                // Extrai dados do HTML (simplificado)
                val title = extractTitleFromHtml(html) ?: "Filme $movieId"
                val posterPath = extractPosterFromHtml(html)
                
                MovieDetails(
                    id = movieId,
                    title = title,
                    overview = "Sinopse não disponível no momento",
                    posterPath = posterPath,
                    backdropPath = posterPath,
                    voteAverage = 7.5,
                    year = "2024"
                )
            } catch (e: Exception) {
                println("❌ Erro ao buscar detalhes: ${e.message}")
                // Retorna dados mockados para não ficar vazio
                MovieDetails(
                    id = movieId,
                    title = getMockTitle(movieId),
                    overview = "Este é um filme/série disponível no SuperFlix.",
                    posterPath = null,
                    backdropPath = null,
                    voteAverage = 7.5,
                    year = "2024"
                )
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
                println("❌ Erro na busca: ${e.message}")
                emptyList()
            }
        }
    }
    
    // Extrai título do HTML
    private fun extractTitleFromHtml(html: String): String? {
        val regex = "<title>(.*?)</title>".toRegex()
        val title = regex.find(html)?.groupValues?.get(1)
        return title?.replace(" - SuperFlix", "")?.replace("SuperFlix", "")?.trim()
    }
    
    // Extrai poster do HTML
    private fun extractPosterFromHtml(html: String): String? {
        // Tenta encontrar imagem do poster
        val regex = """(?:poster_path|img.*src)["']?\s*[:=]\s*["']([^"']+\.(?:jpg|png|jpeg))""".toRegex()
        return regex.find(html)?.groupValues?.get(1)
    }
    
    // Títulos mockados para teste
    private fun getMockTitle(id: String): String {
        return when (id) {
            "tt0068646" -> "O Poderoso Chefão"
            "tt1375666" -> "Inception"
            "tt0133093" -> "Matrix"
            "tt0468569" -> "The Dark Knight"
            "tt0944947" -> "Game of Thrones"
            else -> "Super Filme $id"
        }
    }
}