package com.muryno.server.networkRequest

import com.google.android.gms.maps.model.LatLng
import com.muryno.BuildConfig
import com.muryno.model.CurrentWeatherData
import com.muryno.model.MemoryManager
import com.muryno.model.SubsequenceWeatherData
import com.muryno.server.RetrofitClient.Companion.getInstance
import com.muryno.ui.Repository.saveCurrentWeather
import com.muryno.ui.Repository.saveSubsequentWeather

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeNetwork()  {

    var loc : LatLng?= null

    var apk : String ? = null

    init {

      loc =   MemoryManager().getInstance()?.getLocation()

        apk  = BuildConfig.API_KEY
        fetchCurrentWeatherByLatLon()
    }

    /** get current weather by lat lon**/
     fun fetchCurrentWeatherByLatLon(){
       getInstance()?.getApi()?.getWeatherByLatLon(loc?.latitude , loc?.longitude , apk)?.enqueue(object :Callback<CurrentWeatherData>{

           override fun onFailure(call: Call<CurrentWeatherData>, t: Throwable) { }

           override fun onResponse(call: Call<CurrentWeatherData>, response: Response<CurrentWeatherData>) {
               if (response.isSuccessful && response.body() != null && response.body() != null) {

                   //fetch next 5 days
                   fetchSubWeatherByLatLon()
                   response.body()?.let { saveCurrentWeather(it) }
               }
           }
       })
    }

    /** get next 5 days weather by lat lon **/
     fun fetchSubWeatherByLatLon(){
        getInstance()?.getApi()?.getSubWeatherByLatLon(loc?.latitude , loc?.longitude , apk)?.enqueue(object :Callback<SubsequenceWeatherData>{

            override fun onFailure(call: Call<SubsequenceWeatherData>, t: Throwable) { }

            override fun onResponse(call: Call<SubsequenceWeatherData>, response: Response<SubsequenceWeatherData>) {
                if (response.isSuccessful && response.body() != null && response.body() != null) {
                    response.body()?.let { saveSubsequentWeather(it) }
                }
            }
        })
    }



    /** get current weather by query params**/
     fun fetchCurrentWeatherByLatLon(q : String){
        getInstance()?.getApi()?.getWeatherByQueary(q , apk)?.enqueue(object :Callback<CurrentWeatherData>{

            override fun onFailure(call: Call<CurrentWeatherData>, t: Throwable) { }

            override fun onResponse(call: Call<CurrentWeatherData>, response: Response<CurrentWeatherData>) {
                if (response.isSuccessful && response.body() != null && response.body() != null) {

                    //fetch next 5 days
                    fetchSubWeatherByQuery(q)
                    response.body()?.let { saveCurrentWeather(it) }
                }
            }
        })
    }


    /** get current weather by query params**/
     fun  fetchSubWeatherByQuery(q : String){
        getInstance()?.getApi()?.getSubWeatherByQueary(q , apk)?.enqueue(object :Callback<SubsequenceWeatherData>{

            override fun onFailure(call: Call<SubsequenceWeatherData>, t: Throwable) { }

            override fun onResponse(call: Call<SubsequenceWeatherData>, response: Response<SubsequenceWeatherData>) {
                if (response.isSuccessful && response.body() != null && response.body() != null) {

                    //fetch next 5 days
                    fetchSubWeatherByLatLon()
                    response.body()?.let { saveSubsequentWeather(it) }
                }
            }
        })
    }


}