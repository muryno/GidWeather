package com.muryno.server.networkRequest

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.muryno.BuildConfig
import com.muryno.model.CurrentWeatherData
import com.muryno.model.MemoryManager
import com.muryno.model.SubsequenceWeatherData
import com.muryno.server.RetrofitClient.Companion.getInstance
import com.muryno.ui.view.LoadingView
import com.muryno.ui.Repository.saveCurrentWeather
import com.muryno.ui.Repository.saveSubsequentWeather
import com.muryno.utils.Current_weather

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeNetwork(var callBack : LoadingView)  {


    var apk : String ? = null

    var loc : LatLng ?= null
    init {


      loc =   MemoryManager().getInstance()?.getLocation()

        apk  = BuildConfig.API_KEY
        fetchCurrentWeatherByLatLon()
    }

    /** get current weather by lat lon**/
    private fun fetchCurrentWeatherByLatLon(){
      //  callBack.loadingStart()

        getInstance()?.getApi()?.getWeatherByLatLon(loc?.latitude ,  loc?.longitude , apk)?.enqueue(object :Callback<CurrentWeatherData>{

           override fun onFailure(call: Call<CurrentWeatherData>, t: Throwable) {
               Log.d("Tag",t.localizedMessage.toString())
               callBack.loadingFailed("Cant get data at the moment.. Kindly try again")
           }

           override fun onResponse(call: Call<CurrentWeatherData>, response: Response<CurrentWeatherData>) {
               if (response.isSuccessful  && response.body() != null) {


                   //am using same table for current weather data and next five days,i improvise to get current data,
                   //i can decide to store dt in share preference and query by it, but i believe this is more reliable
                   response.body()?.current_weather = Current_weather


                   callBack.loadingSuccessful("success")

                   //fetch next 5 days
                   fetchSubWeatherByLatLon()

                   //save locally to db
                   response.body()?.let { saveCurrentWeather(it) }


               }else{
                   callBack.loadingFailed("Cant get data at the moment.. Kindly try again")
               }
           }
       })
    }


    /** get next 5 days weather by lat lon **/
     fun fetchSubWeatherByLatLon(){
      //  callBack.loadingStart()

        getInstance()?.getApi()?.getSubWeatherByLatLon(loc?.latitude ,  loc?.longitude , apk)?.enqueue(object :Callback<SubsequenceWeatherData>{

            override fun onFailure(call: Call<SubsequenceWeatherData>, t: Throwable) {
                Log.d("Tag",t.localizedMessage.toString())
                callBack.loadingFailed("Cant get data at the moment.. Kindly try again")

            }

            override fun onResponse(call: Call<SubsequenceWeatherData>, response: Response<SubsequenceWeatherData>) {
                if (response.isSuccessful  && response.body() != null) {
                    //saving locally to db
                    response.body()?.let { saveSubsequentWeather(it) }
                }else{
                    callBack.loadingFailed("Cant get data at the moment.. Kindly try again")
                }
            }
        })
    }



    /** get current weather by query params**/
     fun fetchCurrentWeatherByLatLon(q : String){
        callBack.loadingStart()

        getInstance()?.getApi()?.getWeatherByQueary(q , apk)?.enqueue(object :Callback<CurrentWeatherData>{

            override fun onFailure(call: Call<CurrentWeatherData>, t: Throwable) {
                callBack.loadingFailed("Cant get data at the moment.. Kindly try again")

            }

            override fun onResponse(call: Call<CurrentWeatherData>, response: Response<CurrentWeatherData>) {
                if (response.isSuccessful && response.body() != null) {

                    //am using same table for current weather data and next five days,i improvise to get current data,
                    //i can decide to store dt in share preference and query by it, but i believe this is more reliable
                    response.body()?.current_weather = Current_weather


                    //fetch next 5 days
                    fetchSubWeatherByQuery(q)
                    response.body()?.let { saveCurrentWeather(it) }
                    callBack.loadingSuccessful("success")

                }else{
                    callBack.loadingFailed("Cant get data at the moment.. Kindly try again")
                }
            }
        })
    }


    /** get current weather by query params**/
     fun  fetchSubWeatherByQuery(q : String){
        callBack.loadingStart()
        getInstance()?.getApi()?.getSubWeatherByQueary(q , apk)?.enqueue(object :Callback<SubsequenceWeatherData>{

            override fun onFailure(call: Call<SubsequenceWeatherData>, t: Throwable) {
                Log.d("Tag",t.localizedMessage.toString())
                callBack.loadingFailed("Cant get data at the moment.. Kindly try again")

            }

            override fun onResponse(call: Call<SubsequenceWeatherData>, response: Response<SubsequenceWeatherData>) {
                if (response.isSuccessful  && response.body() != null) {

                    //fetch next 5 days
                    fetchSubWeatherByLatLon()
                    response.body()?.let { saveSubsequentWeather(it) }

                    callBack.loadingSuccessful("success")

                }else{
                    callBack.loadingFailed("Cant get data at the moment.. Kindly try again")
                }
            }
        })
    }


}