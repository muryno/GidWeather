package com.muryno.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.muryno.R
import com.muryno.model.CurrentWeatherData
import com.muryno.ui.adapter.WeatherAdapter
import com.muryno.ui.base.BaseActivity
import com.muryno.ui.interfaces.CustomItemClickListener
import com.muryno.ui.viewmodel.HomeViewModel
import com.muryno.utils.getArtResourceForWeatherCondition
import com.muryno.utils.getCurrentDate

import kotlinx.android.synthetic.main.activity_main.*
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

        //instantiating adapter
        adapter = WeatherAdapter(this)
        weather_RecyclerView.adapter = adapter



        //initialize view model
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        //observe  weather data
        viewModel?.getWeather?.observe(this, Observer {
            views(it)
        })

        //observe next 5 days weather data and pass to adapter
        viewModel?.getSubWeather?.observe(this, Observer {
            it?.weathers?.let { it1 -> adapter?.swapData(it1) }
        })


        viewModel?.loading?.observe(this, Observer {
            progressDialog.visibility = View.VISIBLE

        })


        viewModel?.success?.observe(this, Observer {
            progressDialog.visibility = View.GONE
        })



        viewModel?.failure?.observe(this, Observer {
            progressDialog.visibility = View.GONE
        })


        //search view
        toolbarSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: kotlin.String?): Boolean {
                if (toolbarSearchView.query.isNotEmpty()) {
                    viewModel?.postQuery(toolbarSearchView.query.toString())
                }
                return false
            }

            override fun onQueryTextChange(newText: kotlin.String?): Boolean {

                return false
            }


        })
    }






    //item click
    override fun onItemClick(vararg args: CurrentWeatherData?) {
        if(args.isNotEmpty()) {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("data", args[0])
            startActivity(intent)
        }
    }


    private fun views(dt : CurrentWeatherData?){
        txt_region.text = dt?.name ?: "Loading"
        //because the time stamp return from the api is not correct, i had to improvise ussing current date
        date_.text = getCurrentDate()
        txt_temp.text = String.format(getString(R.string.weather_degree), dt?.main?.temp ?: 0.0)
        img_icon.setImageResource(getArtResourceForWeatherCondition(dt?.weather?.get(0)?.id ?: 200))
        txt_forcast.text = dt?.weather?.get(0)?.main ?: "Loading"
        txt_wind.text = dt?.Wind?.deg?.toString() ?: "0"
        txt_pres.text = dt?.main?.pressure?.toString()  ?: "0"
        txt_humidity.text = dt?.main?.humidity?.toString()  ?: "0"
    }
}
