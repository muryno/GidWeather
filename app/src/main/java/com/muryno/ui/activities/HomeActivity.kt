package com.muryno.ui.activities

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.muryno.R
import com.muryno.model.CurrentWeatherData
import com.muryno.ui.adapter.WeatherAdapter
import com.muryno.ui.base.BaseActivity
import com.muryno.ui.interfaces.CustomItemClickListener
import com.muryno.ui.viewmodel.HomeViewModel
import com.muryno.utils.getArtResourceForWeatherCondition
import com.muryno.utils.getFormattedDate
import com.muryno.utils.getFormattedWind
import kotlinx.android.synthetic.main.content_activity.*
import java.lang.String

class HomeActivity : BaseActivity(), CustomItemClickListener<CurrentWeatherData> {

    private var adapter: WeatherAdapter? = null

    private var viewModel: HomeViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subViews()
    }

    private fun subViews(){


        adapter = WeatherAdapter(this)
        weather_RecyclerView.adapter = adapter



        //initialize view model
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        viewModel?.getCurrentWeather?.observe(this, Observer {
            views(it)
        })

        viewModel?.getSubWeather?.observe(this, Observer {
            it.currentWeathers?.let { it1 -> adapter?.swapData(it1) }
        })
    }

    override fun onItemClick(vararg args: CurrentWeatherData?) {

    }


    fun views(dt : CurrentWeatherData){
        txt_region.text = dt.name.toString()
        date_.text = dt.dt?.toLong()?.let { getFormattedDate(it) }
        txt_temp.text = String.format(getString(R.string.weather_degree), dt.main?.temp)
        img_icon.setImageResource(getArtResourceForWeatherCondition(dt.weather?.get(0)?.id ?: 200))
        txt_forcast.text = dt.weather?.get(0)?.main
        txt_wind.text = dt.Wind?.deg?.toFloat()?.let { getFormattedWind(dt.Wind?.speed ?:0.0, it) }
        txt_pres.text = dt.main?.pressure.toString()
        txt_humidity.text = dt.main?.humidity.toString()
    }
}
