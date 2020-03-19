package com.muryno.model.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.muryno.model.entity.Clouds
import com.muryno.model.entity.Main


class CloudsConverter {
    @TypeConverter
        fun toString(source : Clouds?): String {
        return Gson().toJson(source)
    }

    @TypeConverter
    fun toNews(s: String?): Clouds? {
        return try {
            Gson().fromJson(s, Clouds::class.java)
        } catch (e: Exception) {
            null
        }
    }


}