package com.superflix.app.data.cache

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.superflix.app.data.models.Movie
import com.superflix.app.data.models.Series

class LocalCache(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("superflix_cache", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveMovies(category: String, movies: List<Movie>) {
        val json = gson.toJson(movies)
        prefs.edit().putString("movies_$category", json).apply()
    }

    fun getMovies(category: String): List<Movie> {
        val json = prefs.getString("movies_$category", null) ?: return emptyList()
        val type = object : TypeToken<List<Movie>>() {}.type
        return gson.fromJson(json, type)
    }

    fun saveSeries(category: String, series: List<Series>) {
        val json = gson.toJson(series)
        prefs.edit().putString("series_$category", json).apply()
    }

    fun getSeries(category: String): List<Series> {
        val json = prefs.getString("series_$category", null) ?: return emptyList()
        val type = object : TypeToken<List<Series>>() {}.type
        return gson.fromJson(json, type)
    }

    fun saveFavorite(id: String, isMovie: Boolean) {
        val key = if (isMovie) "favorite_movies" else "favorite_series"
        val favorites = getFavorites(isMovie).toMutableSet()
        if (favorites.contains(id)) favorites.remove(id) else favorites.add(id)
        prefs.edit().putStringSet(key, favorites).apply()
    }

    fun getFavorites(isMovie: Boolean): Set<String> {
        val key = if (isMovie) "favorite_movies" else "favorite_series"
        return prefs.getStringSet(key, emptySet()) ?: emptySet()
    }
}