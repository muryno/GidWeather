package com.muryno.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.muryno.R
import com.muryno.model.CurrentWeatherData
import com.muryno.utils.*
import kotlinx.android.synthetic.main.content_details.*
import java.lang.String

class DetailsActivity : AppCompatActivity() {



    var weather: CurrentWeatherData? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)



        if (intent != null) {
            weather = intent.getSerializableExtra("data") as CurrentWeatherData
            if (weather == null) {
                finish()
            }

            updateView(weather)
        }

        cancel.setOnClickListener {
            finish()
        }

    }


    private fun updateView(cw : CurrentWeatherData?){


        detail_day.text = getFormattedDate(cw?.dt_txt, day)
        cloud_detail.text = cw?.weather?.get(0)?.main ?: "Clear"
        detail_date.text =  getFormattedDate(cw?.dt_txt,month)
        detail_high_textview.text = String.format(getString(R.string.weather_degree), cw?.main?.temp_max ?: 0.0)
        detail_low_textview.text = String.format(getString(R.string.weather_degree), cw?.main?.temp_min ?: 0.0)
        detail_icon.setImageResource(getArtResourceForWeatherCondition(cw?.weather?.get(0)?.id ?: 200))
        hum_txt.text = String.format(getString(R.string.format_humidity), cw?.main?.humidity?.toDouble() ?: 0.0)
        pres_txt.text = String.format(getString(R.string.format_pressure), cw?.main?.pressure?.toDouble() ?: 0.0)
        sea_txt.text = String.format(getString(R.string.format_pressure), cw?.main?.sea_level?.toDouble() ?: 0.0)
        grn_level.text = String.format(getString(R.string.format_pressure), cw?.main?.grnd_level?.toDouble() ?: 0.0)
        win_txt.text = String.format(getString(R.string.format_wind_kmh), cw?.Wind?.speed ?: 0.0)

    }
}
