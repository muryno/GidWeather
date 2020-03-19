package com.muryno.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import android.text.format.DateFormat
import com.google.android.gms.maps.model.LatLng
import com.muryno.MainApplication
import com.muryno.R
import java.io.IOException
import java.text.SimpleDateFormat

import java.util.*


fun isOnline(): Boolean {

    if (MainApplication.instance?.applicationContext == null)
        return false

    val cm = MainApplication.instance!!.applicationContext
        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager



    val activeNetwork = cm.activeNetwork
    return activeNetwork != null
}




fun formatTemperature(
    temperature: Double): String? {
    // For presentation, assume the user doesn't care about tenths of a degree.
    return MainApplication.instance?.getString(R.string.format_temperature)?.let { String.format(it, temperature * 1.8 + 32) }
}



// Format used for storing dates in the database.  ALso used for converting those strings
// back into date objects for comparison/processing.
const val DATE_FORMAT = "yyyyMMdd"



fun getFormattedDate(smsTimeInMilis: Long): String? {
    val smsTime = Calendar.getInstance()
    smsTime.timeInMillis = smsTimeInMilis
    val now = Calendar.getInstance()
    val timeFormatString = "h:mm aa"
    val dateTimeFormatString = "EEEE, MMMM d, h:mm aa"
    val HOURS = 60 * 60 * 60.toLong()
    return if (now[Calendar.DATE] == smsTime[Calendar.DATE]) {
        "Today " + DateFormat.format(timeFormatString, smsTime)
    } else if (now[Calendar.DATE] - smsTime[Calendar.DATE] == 1) {
        "Yesterday " + DateFormat.format(timeFormatString, smsTime)
    } else if (now[Calendar.YEAR] == smsTime[Calendar.YEAR]) {
        DateFormat.format(dateTimeFormatString, smsTime).toString()
    } else {
        DateFormat.format("MMMM dd yyyy, h:mm aa", smsTime).toString()
    }
}


/**
 * Given a day, returns just the name to use for that day.
 * E.g "today", "tomorrow", "wednesday".
 *
 * @param context Context to use for resource localization
 * @param dateInMillis The date in milliseconds
 * @return
 */
fun getDayName( dateInMillis: Long): String? {
    val c = Calendar.getInstance()

    c.timeInMillis = dateInMillis
    val mYear = c[Calendar.YEAR]
    val mMonth = c[Calendar.MONTH]
    val mDay = c[Calendar.DAY_OF_WEEK]
    val hr = c[Calendar.HOUR]
    val min = c[Calendar.MINUTE]
    val sec = c[Calendar.SECOND]



    return mDay.toString()
}

/**
 * Converts db date format to the format "Month day", e.g "June 24".
 * @param context Context to use for resource localization
 * @param dateInMillis The db formatted date string, expected to be of the form specified
 * in Utility.DATE_FORMAT
 * @return The day in the form of a string formatted "December 6"
 */
fun getFormattedMonthDay(context: Context?, dateInMillis: Long): String? {
    val monthDayFormat = SimpleDateFormat("MMMM dd")
    return monthDayFormat.format(dateInMillis)
}

fun getFormattedWind(windSpeed: Double, degrees: Float): String? {
    var windSpeed = windSpeed

    val windFormat: Int = R.string.format_wind_kmh
        windSpeed *= .621371192237334f


    // From wind direction in degrees, determine compass direction as a string (e.g NW)
    // You know what's fun, writing really long if/else statements with tons of possible
    // conditions.  Seriously, try it!
    var direction = "Unknown"
    if (degrees >= 337.5 || degrees < 22.5) {
        direction = "N"
    } else if (degrees >= 22.5 && degrees < 67.5) {
        direction = "NE"
    } else if (degrees >= 67.5 && degrees < 112.5) {
        direction = "E"
    } else if (degrees >= 112.5 && degrees < 157.5) {
        direction = "SE"
    } else if (degrees >= 157.5 && degrees < 202.5) {
        direction = "S"
    } else if (degrees >= 202.5 && degrees < 247.5) {
        direction = "SW"
    } else if (degrees >= 247.5 && degrees < 292.5) {
        direction = "W"
    } else if (degrees >= 292.5 && degrees < 337.5) {
        direction = "NW"
    }
    return MainApplication.instance?.getString(windFormat)?.let { String.format(it, windSpeed, direction) }
}

/**
 * Helper method to provide the icon resource id according to the weather condition id returned
 * by the OpenWeatherMap call.
 * @param weatherId from OpenWeatherMap API response
 * @return resource id for the corresponding icon. -1 if no relation is found.
 */
fun getIconResourceForWeatherCondition(weatherId: Int): Int {

    if (weatherId in 200..232) {
        return R.drawable.ic_storm
    } else if (weatherId in 300..321) {
        return R.drawable.ic_light_rain
    } else if (weatherId in 500..504) {
        return R.drawable.ic_rain
    } else if (weatherId == 511) {
        return R.drawable.ic_snow
    } else if (weatherId in 520..531) {
        return R.drawable.ic_rain
    } else if (weatherId in 600..622) {
        return R.drawable.ic_snow
    } else if (weatherId in 701..761) {
        return R.drawable.ic_fog
    } else if (weatherId == 761 || weatherId == 781) {
        return R.drawable.ic_storm
    } else if (weatherId == 800) {
        return R.drawable.ic_clear
    } else if (weatherId == 801) {
        return R.drawable.ic_light_clouds
    } else if (weatherId in 802..804) {
        return R.drawable.ic_cloudy
    }
    return -1
}

/**
 * Helper method to provide the art resource id according to the weather condition id returned
 * by the OpenWeatherMap call.
 * @param weatherId from OpenWeatherMap API response
 * @return resource id for the corresponding icon. -1 if no relation is found.
 */
fun getArtResourceForWeatherCondition(weatherId: Int): Int {

    if (weatherId in 200..232) {
        return R.drawable.art_storm
    } else if (weatherId in 300..321) {
        return R.drawable.art_light_rain
    } else if (weatherId in 500..504) {
        return R.drawable.art_rain
    } else if (weatherId == 511) {
        return R.drawable.art_snow
    } else if (weatherId in 520..531) {
        return R.drawable.art_rain
    } else if (weatherId in 600..622) {
        return R.drawable.art_snow
    } else if (weatherId in 701..761) {
        return R.drawable.art_fog
    } else if (weatherId == 761 || weatherId == 781) {
        return R.drawable.art_storm
    } else if (weatherId == 800) {
        return R.drawable.art_clear
    } else if (weatherId == 801) {
        return R.drawable.art_light_clouds
    } else if (weatherId in 802..804) {
        return R.drawable.art_clouds
    }
    return -1
}



fun getLocationAddress(location : LatLng, context: Context) : String{
    val gcd =  Geocoder(context, Locale.getDefault())
    var latLng =  LatLng(location.latitude, location.longitude)
    val lng : Double= latLng.longitude
    val  lat : Double = latLng.latitude
    var addresses:  List<Address>?  = null
    try {
        addresses = gcd.getFromLocation(lat, lng, 1)
    } catch (e : IOException) {
        e.printStackTrace()
    }
    var locality : String? = null

    if (addresses != null && addresses.isNotEmpty()) {
        locality =   addresses[0].getAddressLine(0)
    }

    return locality.toString()

}
