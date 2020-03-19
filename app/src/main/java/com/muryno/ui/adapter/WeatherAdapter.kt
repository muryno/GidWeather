package com.muryno.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.muryno.R
import com.muryno.model.CurrentWeatherData

import com.muryno.ui.interfaces.CustomItemClickListener
import com.muryno.utils.formatTemperature
import com.muryno.utils.getArtResourceForWeatherCondition
import com.muryno.utils.getDayName
import com.muryno.utils.getFormattedDate
import kotlinx.android.synthetic.main.forcast_layout.view.*


class WeatherAdapter( private val listener: CustomItemClickListener<CurrentWeatherData>): RecyclerView.Adapter<WeatherAdapter.WeatherAdapterViewHolder>() {

    private var weather : List<CurrentWeatherData>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherAdapterViewHolder {
        val mLayoutInflater = LayoutInflater.from(parent.context)
        var viewHolder: WeatherAdapterViewHolder?=null

        val view : View = mLayoutInflater.inflate(R.layout.forcast_layout, parent, false)

        view.setOnClickListener { listener.onItemClick(viewHolder?.adapterPosition?.let { weather?.get(it) }) }

        viewHolder = WeatherAdapterViewHolder(view)
        return viewHolder    }

    override fun getItemCount(): Int {
       return weather?.size ?: 0
    }


    fun swapData(weather: List<CurrentWeatherData>) {
        this.weather = weather
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: WeatherAdapterViewHolder, position: Int) {
        val weath = weather?.get(position)

        val hf = weath?.main?.temp?.let { formatTemperature(it) }
        holder.temp.text =hf.toString()

        holder.clou_condition.text = weath?.weather?.get(0)?.main


        holder.txt_dat.text = weath?.dt?.toLong()?.let { getFormattedDate(it) }

        holder.img_icon.setImageResource(getArtResourceForWeatherCondition(weath?.weather?.get(0)?.id ?: 200))
    }

    class WeatherAdapterViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        val clou_condition: TextView = itemView.clou_condition
        val txt_dat : TextView = itemView.txt_dat

        val img_icon : ImageView = itemView.img

        val temp : TextView = itemView.temp

    }
}