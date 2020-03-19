package com.muryno.ui.activities

import android.content.Intent
import android.os.Bundle
import com.muryno.model.MemoryManager
import com.muryno.ui.base.BaseActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkLocation()
    }


    private fun checkLocation() {
        if (MemoryManager().getInstance()?.getLocation() != null) {
            val intent = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(intent)
        }
        finish()
    }
}
