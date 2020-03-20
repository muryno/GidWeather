package com.muryno.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muryno.model.CurrentWeatherData
import com.muryno.model.MemoryManager
import com.muryno.model.SubsequenceWeatherData
import com.muryno.server.networkRequest.HomeNetwork
import com.muryno.ui.Repository
import com.muryno.ui.Repository.getCurrentWeather

import java.io.File

class HomeViewModel : ViewModel() {


    fun getCurrentWeather() :  LiveData<CurrentWeatherData>? {
       val dt:Int = MemoryManager().getInstance()?.getQueryParamms() ?:0
      return  getCurrentWeather(dt)
    }

    val getSubWeather  : LiveData<SubsequenceWeatherData>? =  Repository.getSubsequentWeather


    //make network call
    var   networkPresenter  = HomeNetwork()


    fun postQuery(q : String){
        networkPresenter.fetchCurrentWeatherByLatLon(q)
    }



}