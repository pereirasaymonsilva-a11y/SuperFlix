package com.superflix.app.data.api

import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.superflix.app.data.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class SuperFlixApi {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        .build()
    private val gson = Gson()
    private val baseUrl = "https://superflixapi.fit"

    suspend fun getMovies(category: String = "filme"): List<Movie> = withContext(Dispatchers.IO) {
        val url = "$baseUrl/lista?category=${category}s&format=json"
        val json = makeRequest(url)
        parseMovieList(json)
    }

    suspend fun getSeries(category: String = "serie"): List<Series> = withContext(Dispatchers.IO) {
        val url = "$baseUrl/lista?category=${category}s&format=json"
        val json = makeRequest(url)
        parseSeriesList(json)
    }

    suspend fun search(query: String): List<SearchResult> = withContext(Dispatchers.IO) {
        val url = "$baseUrl/lista?category=pesquisa&q=${java.net.URLEncoder.encode(query, "UTF-8")}&format=json"
        val json = makeRequest(url)
        parseSearchResults(json)
    }

    suspend fun getCalendar(): JsonArray = withContext(Dispatchers.IO) {
        val url = "$baseUrl/calendario.php"
        val json = makeRequest(url)
        try {
            gson.fromJson(json, JsonArray::class.java)
        } catch (e: Exception) {
            JsonArray()
        }
    }

    fun getPlayerUrl(type: String, id: String, season: Int = 0, episode: Int = 0): String {
        return if (type == "filme") {
            "$baseUrl/filme/$id#noEpList&color:ff0000&transparent"
        } else {
            "$baseUrl/serie/$id/$season/$episode#noEpList&color:ff0000&transparent"
        }
    }

    private suspend fun makeRequest(url: String): String = withContext(Dispatchers.IO) {
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
        response.body?.string() ?: "[]"
    }

    private fun parseMovieList(json: String): List<Movie> {
        val movies = mutableListOf<Movie>()
        try {
            val array = gson.fromJson(json, JsonArray::class.java)
            for (i in 0 until array.size()) {
                val obj = array[i].asJsonObject
                val movie = Movie(
                    id = obj.get("id")?.asString ?: "",
                    title = obj.get("title")?.asString ?: obj.get("name")?.asString ?: "Sem título",
                    originalTitle = obj.get("original_title")?.asString ?: "",
                    overview = obj.get("overview")?.asString ?: "",
                    posterPath = obj.get("poster")?.asString ?: obj.get("image")?.asString ?: "",
                    backdropPath = obj.get("backdrop")?.asString ?: "",
                    year = obj.get("year")?.asString ?: obj.get("release_date")?.asString?.take(4) ?: ""
                )
                if (movie.id.isNotEmpty()) movies.add(movie)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return movies
    }

    private fun parseSeriesList(json: String): List<Series> {
        val series = mutableListOf<Series>()
        try {
            val array = gson.fromJson(json, JsonArray::class.java)
            for (i in 0 until array.size()) {
                val obj = array[i].asJsonObject
                val serie = Series(
                    id = obj.get("id")?.asString ?: "",
                    title = obj.get("name")?.asString ?: obj.get("title")?.asString ?: "Sem título",
                    overview = obj.get("overview")?.asString ?: "",
                    posterPath = obj.get("poster")?.asString ?: obj.get("image")?.asString ?: "",
                    backdropPath = obj.get("backdrop")?.asString ?: "",
                    year = obj.get("year")?.asString ?: obj.get("first_air_date")?.asString?.take(4) ?: ""
                )
                if (serie.id.isNotEmpty()) series.add(serie)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return series
    }

    private fun parseSearchResults(json: String): List<SearchResult> {
        val results = mutableListOf<SearchResult>()
        try {
            val array = gson.fromJson(json, JsonArray::class.java)
            for (i in 0 until array.size()) {
                val obj = array[i].asJsonObject
                val type = obj.get("type")?.asString ?: ""
                if (type == "movie" || type == "filme") {
                    val movie = parseMovieFromJson(obj)
                    results.add(SearchResult.MovieResult(movie))
                } else {
                    val series = parseSeriesFromJson(obj)
                    results.add(SearchResult.SeriesResult(series))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return results
    }

    private fun parseMovieFromJson(obj: com.google.gson.JsonObject): Movie {
        return Movie(
            id = obj.get("id")?.asString ?: "",
            title = obj.get("title")?.asString ?: "Sem título",
            overview = obj.get("overview")?.asString ?: "",
            posterPath = obj.get("poster")?.asString ?: "",
            year = obj.get("year")?.asString ?: ""
        )
    }

    private fun parseSeriesFromJson(obj: com.google.gson.JsonObject): Series {
        return Series(
            id = obj.get("id")?.asString ?: "",
            title = obj.get("name")?.asString ?: "Sem título",
            overview = obj.get("overview")?.asString ?: "",
            posterPath = obj.get("poster")?.asString ?: "",
            year = obj.get("year")?.asString ?: ""
        )
    }
}