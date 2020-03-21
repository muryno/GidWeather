package com.muryno.model

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.muryno.MainApplication

/**Share preference**/

class MemoryManager() {



    private var sInstance: MemoryManager?=null
    private  var mSharedPreferences: SharedPreferences
    private var editor: SharedPreferences.Editor? = null
    private val PREF_NAME = "gidi_weather_app"
    private val PREF_MODE = 0

    private val SHARED_PREFS = "sharedPrefs"
    private val KEY = "query_paramms"

    private val KEY_LAST_LOCATION = "last_known_location"

    init {
        mSharedPreferences = MainApplication.instance!!.getSharedPreferences(PREF_NAME, PREF_MODE)
        editor = mSharedPreferences.edit()
    }




    @Synchronized
    fun getInstance(): MemoryManager? {
        if (sInstance == null) sInstance = MemoryManager()
        return sInstance
    }



    fun saveData(dt : Int?) {
        dt?.let { editor?.putInt(KEY, it) }
        editor?.apply()
    }

    fun getQueryParamms(): Int? {
        return mSharedPreferences.getInt(KEY, 0)
    }

    fun saveLocation(latLng: LatLng?) {
        val gson = Gson()
        val json = gson.toJson(latLng)
        editor?.putString(KEY_LAST_LOCATION, json)
        editor?.commit()
    }

    fun getLocation(): LatLng? {
        if (mSharedPreferences.getString(KEY_LAST_LOCATION, null) != null) {
            val gson = Gson()
            val json = mSharedPreferences.getString(KEY_LAST_LOCATION, null)
            val type  = object : TypeToken<LatLng?>() {}.type
            return gson.fromJson<LatLng>(json, type)
        }
        return LatLng(0.0, 0.0)
    }



}