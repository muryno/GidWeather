package com.muryno.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muryno.model.CurrentWeatherData
import com.muryno.model.SubsequenceWeatherData
import com.muryno.server.networkRequest.HomeNetwork
import com.muryno.ui.view.LoadingView
import com.muryno.ui.Repository

class HomeViewModel : ViewModel(), LoadingView {



    val getWeather  : LiveData<CurrentWeatherData>? =  Repository.getWeather


    //get next 5 days weather fromm local databse
    val getSubWeather  : LiveData<SubsequenceWeatherData>? =  Repository.getSubsequentWeather

    //HomeNetwork class talk to remote server to make request and save the responds to local server
    var   networkPresenter  = HomeNetwork(this)


    var loading= MutableLiveData<String>()


    var fail= MutableLiveData<String>()
    val failure : LiveData<String>
        get() = fail


    var succ= MutableLiveData<String>()
    val success : LiveData<String>
        get() = succ



    //search by text
    fun postQuery(q : String){
        networkPresenter.fetchCurrentWeatherByLatLon(q)
    }

    //loading fail from the server
    override fun loadingFailed(msg: String) {
        fail.value = msg
    }

    //loading success from the server
    override fun loadingSuccessful(msg: String) {
        succ.value = msg
    }

    override fun loadingStart() {
        loading.postValue("")
    }


}