package com.muryno.ui.activities

import android.content.Intent
import android.os.Bundle
import com.muryno.MainApplication
import com.muryno.model.MemoryManager
import com.muryno.ui.base.BaseActivity
import com.muryno.ui.base.BaseLocation


class SplashActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkLocation()
    }



    private fun checkLocation() {

            val intent = Intent(MainApplication.instance, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }


}