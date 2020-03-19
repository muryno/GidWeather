package com.muryno.model.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Weather  : Serializable{

    @SerializedName("id")
    @Expose
    var id: Int? = null


    @SerializedName("main")
    @Expose
    var main: String? = null


    @SerializedName("description")
    @Expose
    var description: String? = null


    @SerializedName("icon")
    @Expose
    var icon: String? = null







}