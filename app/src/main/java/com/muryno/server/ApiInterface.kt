package com.muryno.server

import com.muryno.model.CurrentWeatherData
import com.muryno.model.SubsequenceWeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

        @GET("weather")
        fun getWeatherByLatLon(@Query("lat") lat: Double?, @Query("lon") lon: Double?, @Query("appid") appkey: String?): Call<CurrentWeatherData>


        @GET("forecast")
        fun getSubWeatherByLatLon(@Query("lat") lat: Double?,@Query("lon") lon: Double?,@Query("appid") appkey: String?): Call<SubsequenceWeatherData>



        @GET("weather")
        fun getWeatherByQueary(@Query("q") q: String?,@Query("appid") appkey: String?): Call<CurrentWeatherData>

        @GET("forecast")
        fun getSubWeatherByQueary(@Query("q") q: String?,@Query("appid") appkey: String?): Call<SubsequenceWeatherData>

}


