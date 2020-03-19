package com.muryno.ui.base

import android.annotation.SuppressLint
import android.location.Location
import androidx.annotation.LayoutRes
import com.akhgupta.easylocation.EasyLocationAppCompatActivity
import com.akhgupta.easylocation.EasyLocationRequestBuilder
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.LatLng
import com.muryno.model.MemoryManager


@SuppressLint("Registered")
open class MapBaseActivity : EasyLocationAppCompatActivity() {
    private var currentLocation: LatLng? = null

    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)
        findUserLocation()
    }

    private fun findUserLocation() {
        val locationRequest = LocationRequest()
            .setInterval(10000)
            .setFastestInterval(5000)
            .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
        val easyLocationRequest = EasyLocationRequestBuilder()
            .setLocationRequest(locationRequest)
            .setFallBackToLastLocationTime(5000)
            .build()
        requestLocationUpdates(easyLocationRequest)
    }

    override fun onLocationPermissionGranted() {}
    override fun onLocationPermissionDenied() {}
    override fun onLocationReceived(location: Location) {
        if (location == null) return
        currentLocation = LatLng(location.latitude, location.longitude)
        MemoryManager().getInstance()?.saveLocation(currentLocation)
    }

    override fun onLocationProviderEnabled() {}
    override fun onLocationProviderDisabled() {}
}