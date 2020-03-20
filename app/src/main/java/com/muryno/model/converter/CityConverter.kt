package com.muryno.model.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.muryno.model.entity.City
import com.muryno.model.entity.Coord
import com.muryno.model.entity.Main


class CityConverter {
    @TypeConverter
        fun toString(source : City?): String {
        return Gson().toJson(source)
    }

    @TypeConverter
    fun toCity(s: String?): City? {
        return try {
            Gson().fromJson(s, City::class.java)
        } catch (e: Exception) {
            null
        }
    }


}