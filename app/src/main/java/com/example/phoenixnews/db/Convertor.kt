package com.example.phoenixnews.db

import androidx.room.TypeConverter
import com.example.phoenixnews.model.Source

class Convertor {
    @TypeConverter
    fun fromSource(source: Source?): String? {
        return source?.name
    }

    @TypeConverter
    fun toSource(name: String?): Source? {
        return name?.let { Source(it, it) }
    }
}