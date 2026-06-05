package com.superflix.app.data.database

import androidx.room.TypeConverter
import com.superflix.app.data.models.MediaType

class Converters {
    @TypeConverter
    fun fromMediaType(type: MediaType): String {
        return type.name
    }
    
    @TypeConverter
    fun toMediaType(type: String): MediaType {
        return MediaType.valueOf(type)
    }
}