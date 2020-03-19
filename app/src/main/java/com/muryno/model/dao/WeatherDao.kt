package com.muryno.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muryno.model.CurrentWeatherData
import com.muryno.model.SubsequenceWeatherData


@Dao
interface WeatherDao {


    /**current weather data**/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCurrentWeather(weather: CurrentWeatherData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCurrentWeather(weather: List<CurrentWeatherData>)

    @Query("SELECT * FROM current_weather_data ORDER BY dt DESC")
    fun getCurrentWeather(): LiveData<CurrentWeatherData>



    /**next 5 days weather data**/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveSubWeather(weather: SubsequenceWeatherData)


    @Query("SELECT * FROM subsequence_weather_data")
    fun getCurrentSubWeather(): LiveData<SubsequenceWeatherData>





}