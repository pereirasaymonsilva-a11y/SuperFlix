package com.superflix.app.data.repositories

import com.superflix.app.data.api.SuperFlixApi
import com.google.gson.JsonArray

class CalendarRepository(
    private val api: SuperFlixApi
) {
    suspend fun getCalendar(): JsonArray {
        return try {
            api.getCalendar()
        } catch (e: Exception) {
            JsonArray()
        }
    }
}