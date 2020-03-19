package com.muryno.model.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.muryno.model.entity.Clouds
import com.muryno.model.entity.Main
import com.muryno.model.entity.Rain
import com.muryno.model.entity.Sys


class RainConverter {
    @TypeConverter
        fun toString(source : Rain?): String {
        return Gson().toJson(source)
    }

    @TypeConverter
    fun toSYS(s: String?): Rain? {
        return try {
            Gson().fromJson(s, Rain::class.java)
        } catch (e: Exception) {
            null
        }
    }


}