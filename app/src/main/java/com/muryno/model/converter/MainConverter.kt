package com.muryno.model.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.muryno.model.entity.Main


class MainConverter {
    @TypeConverter
        fun toString(source : Main?): String {
        return Gson().toJson(source)
    }

    @TypeConverter
    fun toNews(s: String?): Main? {
        return try {
            Gson().fromJson(s, Main::class.java)
        } catch (e: Exception) {
            null
        }
    }


}