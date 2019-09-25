package com.kennygunderman.ombrello

import android.app.Application
import com.kennygunderman.ombrello.di.apiModule
import com.kennygunderman.ombrello.di.locationServiceModule
import com.kennygunderman.ombrello.di.utilModule
import com.kennygunderman.ombrello.di.vmModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class OmbrelloApplication: Application() {

    private val appModules = listOf(
        vmModule,
        apiModule,
        locationServiceModule,
        utilModule
    )

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@OmbrelloApplication)
            modules(appModules)
        }
    }
}
