package com.muryno.utiltest

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.muryno.R
import com.muryno.utils.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UtilsUnitTest {

    @Test
    fun checkDeviceOnline() {
        //check if device is online or offline
        assertEquals(true, isOnline())
    }


    @Test
    fun formatTemperatureNotEmpty(){
        assertNotNull(formatTemperature(20.0))
    }

    @Test
    fun formatTemperatureEqual(){
        val result = "68°"
        assertEquals(result,formatTemperature(20.0))
    }

    @Test
    fun getFormattedDateWithNullData(){
        val dateFormat = null
        assertNotNull(getFormattedDate(dateFormat,day))
    }

    @Test
    fun getDayFromFormattedDateWitDateString(){
        val dateFormat = "2020-03-21 09:00:00"
        val  result = "Sat"   //mind the upper case incase you want to test with your date
        assertEquals(result,getFormattedDate(dateFormat,day))
    }

    @Test
    fun getMonthFromFormattedDateWitDateString(){
        val dateFormat = "2020-03-21 09:00:00"
        val  result = "21 Mar"   //mind the upper case incase you want to test with your date
        assertEquals(result,getFormattedDate(dateFormat, month))
    }

    @Test
    fun getTimeFromFormattedDateWitDateString(){
        val dateFormat = "2020-03-21 09:00:00"
        val  result = "09:00"   //mind the upper case incase you want to test with your date
        assertEquals(result,getFormattedDate(dateFormat, time))
    }

    @Test
    fun getCurrentDateNotNullData(){
        assertNotNull(getCurrentDate())
    }

    @Test
    fun getArtResourceForWeatherConditionByWeatherId() {
        val weather_id = 204
        assertEquals(R.drawable.art_storm,getArtResourceForWeatherCondition(weather_id))
    }

    @Test
    fun getArtResourceForWeatherConditionWithZeroNotNull() {
        val weather_id = 0
        assertNotNull( getArtResourceForWeatherCondition(weather_id))
    }
}
