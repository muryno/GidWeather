package com.muryno.model.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Sys  : Serializable{

    @SerializedName("type")
    @Expose
    var type: Int? = null


    @SerializedName("id")
    @Expose
    var id: Int? = null


    @SerializedName("country")
    @Expose
    var country: String? = null


    @SerializedName("sunrise")
    @Expose
    var sunrise: Int? = null

    @SerializedName("sunset")
    @Expose
    var sunset: Int? = null

    @SerializedName("pod")
    @Expose
    var pod: String? = null



}