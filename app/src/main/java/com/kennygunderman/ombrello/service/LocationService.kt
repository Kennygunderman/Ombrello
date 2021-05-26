package com.kennygunderman.ombrello.service

import android.Manifest
import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager


val LOCATION_REQUEST = 100

open class LocationService(private val locationManager: LocationManager, private val geocoder: Geocoder) {
    interface Callback {
        fun hasPermission(perm: String): Boolean
        fun permRequestNeeded(perm: String)
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
    fun findLatLongFromLocation(): Pair<Double, Double>? {
        return if (hasPerms()) {
            val location = getLocation()
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

    fun getCity(): String? {
       return findLatLongFromLocation()?.let {
            val addresses: List<Address> = geocoder.getFromLocation(it.first, it.second, 1)
            addresses[0].getLocality()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(): Location? {
        val providers: List<String> = locationManager.getProviders(true)
        var bestLocation: Location? = null
        for (provider in providers) {
            val location: Location = locationManager.getLastKnownLocation(provider) ?: continue
            if (bestLocation == null || location.accuracy < bestLocation.accuracy) {
                bestLocation = location
            }
        }
        return bestLocation
    }

    private fun hasPerms(): Boolean = callback?.hasPermission(perm) == true
}