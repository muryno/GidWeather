package com.muryno.ui

import androidx.lifecycle.LiveData
import com.muryno.MainApplication
import com.muryno.model.AppDatabase.Companion.getAppDataBase
import com.muryno.model.CurrentWeatherData
import com.muryno.model.MemoryManager
import com.muryno.model.SubsequenceWeatherData
import com.muryno.utils.Current_weather


object Repository {

    init {

    }


    /**save current weather to local database**/
    fun saveCurrentWeather(data: CurrentWeatherData) {
        //because date change, i had to delete the previous record stored locally
        MainApplication.executorService.execute {
            getAppDataBase()?.weatherDao()?.nukeCurrWeather()
            getAppDataBase()?.weatherDao()?.saveCurrentWeather(data)
        }
    }



    /**get current weather to local database**/
    val getWeather:  LiveData<CurrentWeatherData>?
        get() = getAppDataBase()?.weatherDao()?.getCurrentWeather(Current_weather)





    /**save subsequent weather to local database**/
    fun saveSubsequentWeather(data: SubsequenceWeatherData) {
        //because date change, i had to delete the previous record stored locally

        MainApplication.executorService.execute {
            getAppDataBase()?.weatherDao()?.nukeWeather()
            getAppDataBase()?.weatherDao()?.saveSubWeather(data)
        }
    }
    /**get subsequent weather to local database**/
    val getSubsequentWeather:  LiveData<SubsequenceWeatherData>?
        get() = getAppDataBase()?.weatherDao()?.getCurrentSubWeather()




}//
