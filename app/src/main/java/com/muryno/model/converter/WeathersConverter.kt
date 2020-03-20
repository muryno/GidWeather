package com.muryno.model.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.muryno.model.entity.Weather
import java.util.*

class WeathersConverter {
    @TypeConverter
    fun toString(stock: ArrayList<Weather>?): String? {
        return Gson().toJson(stock)
    }

    @TypeConverter
    fun toWeather(s: String?): ArrayList<Weather>? {
        return try {
            Gson().fromJson<ArrayList<Weather>>(
                s,
                object : TypeToken<ArrayList<Weather>?>() {}.type
            )
        } catch (e: Exception) {
            null
        }
    }
}