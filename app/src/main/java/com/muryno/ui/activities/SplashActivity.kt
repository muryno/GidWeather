package com.muryno.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.muryno.MainApplication
import com.muryno.model.MemoryManager
import com.muryno.ui.base.BaseActivity
import com.muryno.ui.base.BaseLocation
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class SplashActivity : BaseLocation() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //request for location and on success to move to HomeActivity is handled in BaseLocation()
        requestNewLocationData()

    }





}