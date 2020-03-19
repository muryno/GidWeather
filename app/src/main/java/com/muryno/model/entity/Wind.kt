package com.muryno.model.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Wind  : Serializable{

    @SerializedName("speed")
    @Expose
    var speed: Double? = null


    @SerializedName("deg")
    @Expose
    var deg: Int? = null


}