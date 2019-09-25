package com.kennygunderman.ombrello.service

import android.Manifest
import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.LocationManager

class LocationService(private val locationManager: LocationManager, private val geocoder: Geocoder) {
    interface Callback {
        fun hasPermission(perm: String): Boolean
        fun permRequestNeeded(perm: String)
        val locationRequestCode: Int get() = 100
    }

    var callback: Callback? = null
    private val perm = Manifest.permission.ACCESS_FINE_LOCATION

    private fun requestPermission() {
        if (!hasPerms()) {
            callback?.permRequestNeeded(perm)
        }
    }

    /**
     * Find latitude & longitude from user's current location if GPS Permissions have been provided,
     * if permissions have not been provided then they will be requests.
     *
     * Lint Suppress because we are doing the permission check in [hasPerms]
     *
     * @return Pair
     */
    @SuppressLint("MissingPermission")
    fun findLatLongFromLocation(): Pair<Double, Double>? {
        return if (hasPerms()) {
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            location ?: return null

            val address = geocoder
                .getFromLocation(location.latitude, location.longitude, 1)
                .firstOrNull()

            Pair(address?.latitude ?: 0.0, address?.longitude ?: 0.0)
        } else {
            requestPermission()
            null
        }
    }

    private fun hasPerms(): Boolean = callback?.hasPermission(perm) == true
}