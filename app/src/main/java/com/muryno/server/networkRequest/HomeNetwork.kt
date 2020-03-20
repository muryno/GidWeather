package com.muryno.server.networkRequest

import android.util.Log
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


    var apk : String ? = null

    init {


//      loc =   MemoryManager().getInstance()?.getLocation()

        apk  = BuildConfig.API_KEY
        fetchCurrentWeatherByLatLon()
    }

    /** get current weather by lat lon**/
    private fun fetchCurrentWeatherByLatLon(){
       getInstance()?.getApi()?.getWeatherByLatLon(6.6018 ,  3.3515 , apk)?.enqueue(object :Callback<CurrentWeatherData>{

           override fun onFailure(call: Call<CurrentWeatherData>, t: Throwable) {
               Log.d("Tag",t.localizedMessage.toString())

           }

           override fun onResponse(call: Call<CurrentWeatherData>, response: Response<CurrentWeatherData>) {
               if (response.isSuccessful  && response.body() != null) {

                   //save to share preference to query by dt since am using same table for bot current and next 5 days weather
                   response.body()?.let {  MemoryManager().getInstance()?.saveData(it.dt)}

                   //fetch next 5 days
                   fetchSubWeatherByLatLon()
                   response.body()?.let { saveCurrentWeather(it) }
               }
           }
       })
    }

    /** get next 5 days weather by lat lon **/
     fun fetchSubWeatherByLatLon(){
        getInstance()?.getApi()?.getSubWeatherByLatLon(6.6018 ,  3.3515 , apk)?.enqueue(object :Callback<SubsequenceWeatherData>{

            override fun onFailure(call: Call<SubsequenceWeatherData>, t: Throwable) {
                Log.d("Tag",t.localizedMessage.toString())

            }

            override fun onResponse(call: Call<SubsequenceWeatherData>, response: Response<SubsequenceWeatherData>) {
                if (response.isSuccessful  && response.body() != null) {
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
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.let {  MemoryManager().getInstance()?.saveData(it.dt)}

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

            override fun onFailure(call: Call<SubsequenceWeatherData>, t: Throwable) {
                Log.d("Tag",t.localizedMessage.toString())
            }

            override fun onResponse(call: Call<SubsequenceWeatherData>, response: Response<SubsequenceWeatherData>) {
                if (response.isSuccessful  && response.body() != null) {

                    //fetch next 5 days
                    fetchSubWeatherByLatLon()
                    response.body()?.let { saveSubsequentWeather(it) }
                }
            }
        })
    }


}