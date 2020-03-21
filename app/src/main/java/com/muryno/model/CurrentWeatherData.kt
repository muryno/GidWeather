package com.muryno.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.muryno.model.converter.*
import com.muryno.model.entity.*
import java.io.Serializable
import java.util.ArrayList

@Entity(tableName = "current_weather_data")
class CurrentWeatherData  : Serializable{
    @PrimaryKey
    @SerializedName("dt")
    @Expose
    var dt: Int? = null



    @SerializedName("id")
    @Expose
    var id: Int? = null


    @TypeConverters(WeathersConverter::class)
    @SerializedName("weather")
    @Expose
    var weather: ArrayList<Weather>? = null



    @SerializedName("base")
    @Expose
    var base: String? = null


    @TypeConverters(MainConverter::class)
    @SerializedName("main")
    @Expose
    var main: Main? = null


    @SerializedName("visibility")
    @Expose
    var visibility: Int? = null


    @TypeConverters(WindConverter::class)
    @SerializedName("wind")
    @Expose
    var Wind: Wind? = null


    @TypeConverters(CloudsConverter::class)
    @SerializedName("clouds")
    @Expose
    var clouds: Clouds? = null




    @TypeConverters(SysConverter::class)
    @SerializedName("sys")
    var sys: Sys? = null



    @SerializedName("name")
    @Expose
    var name: String? = null


    @TypeConverters(RainConverter::class)
    @SerializedName("rain")
    @Expose
    var rain: Rain? = null


    @SerializedName("dt_txt")
    @Expose
    var dt_txt: String? = null


    var current_weather: Int = 0

}