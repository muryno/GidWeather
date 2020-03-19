package com.muryno.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import android.preference.PreferenceManager
import android.text.format.Time
import com.google.android.gms.maps.model.LatLng
import com.muryno.MainApplication
import com.muryno.R
import java.io.IOException
import java.text.DateFormat
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


fun isMetric(context: Context): Boolean {
    val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    return (prefs.getString(
        context.getString(R.string.pref_units_key),
        context.getString(R.string.pref_units_metric)
    )
            == context.getString(R.string.pref_units_metric))
}


fun formatTemperature(
    context: Context,
    temperature: Double
): String? {
    // Data stored in Celsius by default.  If user prefers to see in Fahrenheit, convert
    // the values here.
    var temperature = temperature
    val suffix = "\u00B0"
    if (!isMetric(context)) {
        temperature = temperature * 1.8 + 32
    }

    // For presentation, assume the user doesn't care about tenths of a degree.
    return String.format(context.getString(R.string.format_temperature), temperature)
}

fun formatDate(dateInMilliseconds: Long): String? {
    val date = Date(dateInMilliseconds)
    return DateFormat.getDateInstance().format(date)
}

// Format used for storing dates in the database.  ALso used for converting those strings
// back into date objects for comparison/processing.
const val DATE_FORMAT = "yyyyMMdd"

/**
 * Helper method to convert the database representation of the date into something to display
 * to users.  As classy and polished a user experience as "20140102" is, we can do better.
 *
 * @param context Context to use for resource localization
 * @param dateInMillis The date in milliseconds
 * @return a user-friendly representation of the date.
 */
fun getFriendlyDayString(
    context: Context,
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
        val today = context.getString(R.string.today)
        val formatId: Int = R.string.format_full_friendly_date
        String.format(
            context.getString(
                formatId,
                today,
                getFormattedMonthDay(context, dateInMillis)
            )
        )
    } else if (julianDay < currentJulianDay + 7) {
        // If the input date is less than a week in the future, just return the day name.
        getDayName(context, dateInMillis)
    } else {
        // Otherwise, use the form "Mon Jun 3"
        val shortenedDateFormat =
            SimpleDateFormat("EEE MMM dd")
        shortenedDateFormat.format(dateInMillis)
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
fun getDayName(context: Context, dateInMillis: Long): String? {
    // If the date is today, return the localized version of "Today" instead of the actual
    // day name.
    val t = Time()
    t.setToNow()
    val julianDay = Time.getJulianDay(dateInMillis, t.gmtoff)
    val currentJulianDay =
        Time.getJulianDay(System.currentTimeMillis(), t.gmtoff)
    return if (julianDay == currentJulianDay) {
        context.getString(R.string.today)
    } else if (julianDay == currentJulianDay + 1) {
        context.getString(R.string.tomorrow)
    } else {
        val time = Time()
        time.setToNow()
        // Otherwise, the format is just the day of the week (e.g "Wednesday".
        val dayFormat = SimpleDateFormat("EEEE")
        dayFormat.format(dateInMillis)
    }
}

/**
 * Converts db date format to the format "Month day", e.g "June 24".
 * @param context Context to use for resource localization
 * @param dateInMillis The db formatted date string, expected to be of the form specified
 * in Utility.DATE_FORMAT
 * @return The day in the form of a string formatted "December 6"
 */
fun getFormattedMonthDay(
    context: Context?,
    dateInMillis: Long
): String? {
    val time = Time()
    time.setToNow()
    val dbDateFormat =
        SimpleDateFormat(DATE_FORMAT)
    val monthDayFormat = SimpleDateFormat("MMMM dd")
    return monthDayFormat.format(dateInMillis)
}

fun getFormattedWind(
    context: Context,
    windSpeed: Float,
    degrees: Float
): String? {
    var windSpeed = windSpeed
    val windFormat: Int
    if (isMetric(context)) {
        windFormat = R.string.format_wind_kmh
    } else {
        windFormat = R.string.format_wind_mph
        windSpeed *= .621371192237334f
    }

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
    return String.format(context.getString(windFormat), windSpeed, direction)
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
