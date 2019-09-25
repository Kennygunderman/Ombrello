package com.kennygunderman.ombrello.di

import android.content.Context
import android.location.Geocoder
import android.location.LocationManager
import com.kennygunderman.ombrello.service.LocationService
import org.koin.dsl.module
import java.util.*

val locationServiceModule = module {
    factory { get<Context>().getSystemService(Context.LOCATION_SERVICE) as LocationManager }
    factory { Geocoder(get(), Locale.US) }
    factory { LocationService(get(), get()) }
}