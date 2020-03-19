package com.muryno.model.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.muryno.model.entity.Clouds
import com.muryno.model.entity.Main
import com.muryno.model.entity.Sys


class SysConverter {
    @TypeConverter
        fun toString(source : Sys?): String {
        return Gson().toJson(source)
    }

    @TypeConverter
    fun toSYS(s: String?): Sys? {
        return try {
            Gson().fromJson(s, Sys::class.java)
        } catch (e: Exception) {
            null
        }
    }


}