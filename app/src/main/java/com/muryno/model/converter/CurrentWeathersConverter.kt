package com.muryno.model.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.muryno.model.CurrentWeatherData
import com.muryno.model.entity.Weather
import java.util.*

class CurrentWeathersConverter {
    @TypeConverter
    fun toString(stock: ArrayList<CurrentWeatherData>): String? {
        return Gson().toJson(stock)
    }

    @TypeConverter
    fun toCurrentWeather(s: String?):ArrayList<CurrentWeatherData>? {
        return try {
            Gson().fromJson<ArrayList<CurrentWeatherData>>(
                s,
                object : TypeToken<ArrayList<CurrentWeatherData>?>() {}.type
            )
        } catch (e: Exception) {
            null
        }
    }
}