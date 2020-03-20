package com.muryno.model.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.muryno.model.entity.Coord
import com.muryno.model.entity.Main


class CoordConverter {
    @TypeConverter
        fun toString(source : Coord?): String {
        return Gson().toJson(source)
    }

    @TypeConverter
    fun toCoord(s: String?): Coord? {
        return try {
            Gson().fromJson(s, Coord::class.java)
        } catch (e: Exception) {
            null
        }
    }


}