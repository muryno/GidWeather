package com.muryno.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.muryno.R
import com.muryno.model.CurrentWeatherData

class DetailsActivity : AppCompatActivity() {



    var currentWeather: CurrentWeatherData? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_details)



        if (intent != null) {
            currentWeather = intent.getSerializableExtra("data") as CurrentWeatherData
            if (currentWeather == null) {
                finish()
            }
        }

    }


    fun updateView(cw : CurrentWeatherData){

    }
}
