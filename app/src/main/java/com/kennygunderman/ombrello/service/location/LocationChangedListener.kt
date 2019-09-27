package com.kennygunderman.ombrello.service.location

import android.location.Location
import android.location.LocationListener
import android.os.Bundle

class LocationChangedListener(private val onChanged: (lat: Double, long: Double) -> Unit): LocationListener {
    override fun onLocationChanged(location: Location?) {
        val lat = location?.latitude ?: 0.0
        val long = location?.longitude ?: 0.0
        onChanged.invoke(lat, long)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        //no-op
    }

    override fun onProviderEnabled(provider: String?) {
        //no-op
    }

    override fun onProviderDisabled(provider: String?) {
        //no-op
    }

}