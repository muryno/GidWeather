package com.muryno.model.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Main  : Serializable{

    @SerializedName("temp")
    @Expose
    var temp: Double? = null


    @SerializedName("feels_like")
    @Expose
    var feels_like: Double? = null


    @SerializedName("temp_min")
    @Expose
    var temp_min: Double? = null


    @SerializedName("temp_max")
    @Expose
    var temp_max: Double? = null

    @SerializedName("pressure")
    @Expose
    var pressure: Int? = null


    @SerializedName("humidity")
    @Expose
    var humidity: Int? = null




    @SerializedName("sea_level")
    @Expose
    var sea_level: Int? = null

    @SerializedName("grnd_level")
    @Expose
    var grnd_level: Int? = null


    @SerializedName("temp_kf")
    @Expose
    var temp_kf: Double? = null

}