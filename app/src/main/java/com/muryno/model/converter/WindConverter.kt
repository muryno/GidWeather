package com.muryno.model.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.muryno.model.entity.Clouds
import com.muryno.model.entity.Main
import com.muryno.model.entity.Wind


class WindConverter {
    @TypeConverter
        fun toString(source : Wind?): String {
        return Gson().toJson(source)
    }

    @TypeConverter
    fun toWind(s: String?): Wind? {
        return try {
            Gson().fromJson(s, Wind::class.java)
        } catch (e: Exception) {
            null
        }
    }


}