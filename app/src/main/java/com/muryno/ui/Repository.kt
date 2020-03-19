package com.muryno.ui

import androidx.lifecycle.LiveData
import com.muryno.MainApplication
import com.muryno.model.AppDatabase.Companion.getAppDataBase
import com.muryno.model.CurrentWeatherData
import com.muryno.model.SubsequenceWeatherData


object Repository {


    /**save current weather to local database**/
    fun saveCurrentWeather(data: CurrentWeatherData) {
        MainApplication.executorService.execute {
            getAppDataBase()?.weatherDao()?.saveCurrentWeather(data)
        }
    }
    /**get current weather to local database**/
    val getCurrentWeather:  LiveData<CurrentWeatherData>?
        get() = getAppDataBase()?.weatherDao()?.getCurrentWeather()





    /**save subsequent weather to local database**/
    fun saveSubsequentWeather(data: SubsequenceWeatherData) {
        MainApplication.executorService.execute {
            getAppDataBase()?.weatherDao()?.saveSubWeather(data)
        }
    }
    /**get subsequent weather to local database**/
    val getSubsequentWeather:  LiveData<SubsequenceWeatherData>?
        get() = getAppDataBase()?.weatherDao()?.getCurrentSubWeather()




}//
