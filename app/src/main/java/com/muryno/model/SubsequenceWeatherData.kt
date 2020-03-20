package com.muryno.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.muryno.model.converter.CityConverter
import com.muryno.model.converter.CurrentWeathersConverter
import com.muryno.model.converter.WeathersConverter
import com.muryno.model.entity.*
import java.io.Serializable

@Entity(tableName = "subsequence_weather_data")
class SubsequenceWeatherData  : Serializable{
    @PrimaryKey
    @SerializedName("cnt")
    @Expose
    var cnt: Int? = null

    @TypeConverters(CityConverter::class)
    @SerializedName("city")
    @Expose
    var city: City? = null


    @TypeConverters(CurrentWeathersConverter::class)
    @SerializedName("list")
    @Expose
    var currentWeathers: ArrayList<CurrentWeatherData>? = null




}