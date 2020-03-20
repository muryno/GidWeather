package com.muryno.utils

import android.content.Context
import android.net.ConnectivityManager
import android.text.format.DateFormat
import android.text.format.Time
import com.muryno.MainApplication
import com.muryno.R
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
    val dateTimeFormatString = "EE, MMMM d, h:mm aa"
    val HOURS = 60 * 60 * 60.toLong()
    return if (now[Calendar.DATE] === smsTime[Calendar.DATE]) {
        "Today " + DateFormat.format(timeFormatString, smsTime)
    } else if (now[Calendar.DATE] - smsTime[Calendar.DATE] === 1) {
        "Yesterday " + DateFormat.format(timeFormatString, smsTime)
    } else if (now[Calendar.YEAR] === smsTime[Calendar.YEAR]) {
        DateFormat.format(dateTimeFormatString, smsTime).toString()
    } else {
        DateFormat.format("MMMM dd yyyy, h:mm aa", smsTime).toString()
    }
}



fun getDayName( dateInMillis: Long): String? {
    // If the date is today, return the localized version of "Today" instead of the actual
    // day name.
    val t = Time()
    t.setToNow()
    val julianDay = Time.getJulianDay(dateInMillis, t.gmtoff)
    val currentJulianDay =
        Time.getJulianDay(System.currentTimeMillis(), t.gmtoff)
    return when (julianDay) {
        currentJulianDay -> {
            MainApplication.instance?.getString(R.string.today)
        }
        currentJulianDay + 1 -> {
            MainApplication.instance?.getString(R.string.tomorrow)
        }
        else -> {
            val time = Time()
            time.setToNow()
            // Otherwise, the format is just the day of the week (e.g "Wednesday".
            val dayFormat = SimpleDateFormat("EEEE")
            dayFormat.format(dateInMillis)
        }
    }
}


fun getFriendlyDayString(
    dateInMillis: Long
): String? {
    // The day string for forecast uses the following logic:
    // For today: "Today, June 8"
    // For tomorrow:  "Tomorrow"
    // For the next 5 days: "Wednesday" (just the day name)
    // For all days after that: "Mon Jun 8"
    val time = Time()
    time.setToNow()
    val currentTime = System.currentTimeMillis()
    val julianDay = Time.getJulianDay(dateInMillis, time.gmtoff)
    val currentJulianDay = Time.getJulianDay(currentTime, time.gmtoff)

    // If the date we're building the String for is today's date, the format
    // is "Today, June 24"
    return if (julianDay == currentJulianDay) {
        val today = MainApplication.instance?.getString(R.string.today)
        val formatId = R.string.format_full_friendly_date
        String.format(
            MainApplication.instance?.getString(formatId, today, getFormattedMonthDay( dateInMillis)
            ).toString()
        )
    } else if (julianDay < currentJulianDay + 7) {
        // If the input date is less than a week in the future, just return the day name.
       getDayName( dateInMillis)
    } else {
        // Otherwise, use the form "Mon Jun 3"
        val shortenedDateFormat =
            SimpleDateFormat("EEE MMM dd")
        shortenedDateFormat.format(dateInMillis)
    }
}



fun getFormattedMonthDay( dateInMillis: Long): String? {
    val monthDayFormat = SimpleDateFormat("MMMM dd")
    return monthDayFormat.format(dateInMillis)
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



