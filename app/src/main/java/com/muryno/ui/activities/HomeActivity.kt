package com.muryno.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
import com.muryno.utils.getDayName
import com.muryno.utils.getFormattedDate
import com.muryno.utils.getFriendlyDayString
import kotlinx.android.synthetic.main.content_activity.*
import java.lang.String

class HomeActivity : AppCompatActivity(), CustomItemClickListener<CurrentWeatherData> {

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

        viewModel?.getCurrentWeather()?.observe(this, Observer {
            views(it)
        })

        viewModel?.getSubWeather?.observe(this, Observer {
            it?.currentWeathers?.let { it1 -> adapter?.swapData(it1) }
        })



        toolbarSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: kotlin.String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: kotlin.String?): Boolean {
                if (toolbarSearchView.query.isNotEmpty()) {

                    viewModel?.postQuery(toolbarSearchView.query.toString())

                }
                return false
            }


        })

    }

    override fun onItemClick(vararg args: CurrentWeatherData?) {
        if(args.isNotEmpty()) {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("data", args[0])
            startActivity(intent)
        }
    }


    fun views(dt : CurrentWeatherData?){
        txt_region.text = dt?.name ?: "Loading"
        date_.text = dt?.dt?.toLong()?.let { getFriendlyDayString(it) } ?: "Today"
        txt_temp.text = String.format(getString(R.string.weather_degree), dt?.main?.temp ?: 0.0)
        img_icon.setImageResource(getArtResourceForWeatherCondition(dt?.weather?.get(0)?.id ?: 200))
        txt_forcast.text = dt?.weather?.get(0)?.main ?: "Loading"
        txt_wind.text = dt?.Wind?.deg?.toString() ?: "0"
        txt_pres.text = dt?.main?.pressure?.toString()  ?: "0"
        txt_humidity.text = dt?.main?.humidity?.toString()  ?: "0"
    }
}
