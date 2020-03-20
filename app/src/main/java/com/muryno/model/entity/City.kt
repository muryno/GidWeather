package com.muryno.model.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.muryno.model.converter.CityConverter
import com.muryno.model.converter.CoordConverter
import java.io.Serializable


class City  : Serializable{

    @PrimaryKey
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @TypeConverters(CoordConverter::class)
    @SerializedName("coord")
    @Expose
    var coord: Coord? = null

    @SerializedName("country")
    @Expose
    var country: String? = null



    @SerializedName("population")
    @Expose
    var population: Int? = null

    @SerializedName("timezone")
    @Expose
    var timezone: Int? = null



    @SerializedName("sunrise")
    @Expose
    var sunrise: Int? = null

    @SerializedName("sunset")
    @Expose
    var sunset: Int? = null



}