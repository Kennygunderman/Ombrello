package com.kennygunderman.ombrello.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.byteunits.DecimalByteUnit
import com.kennygunderman.ombrello.BuildConfig
import com.kennygunderman.ombrello.OmbrelloApplication
import com.kennygunderman.ombrello.service.api.WeatherService
import com.kennygunderman.ombrello.util.DateDeserializer
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.*

val apiModule = module {
    /**
     * Provide [Gson]
     */
    factory<Gson> {
        GsonBuilder().apply {
            registerTypeAdapter(Date::class.java, DateDeserializer())
        }.create()
    }

    /**
     * Provide [OkHttpClient]
     */
    factory {
        val cacheDir = File((get<Context>() as OmbrelloApplication).cacheDir, "http")
        val cache = Cache(
            cacheDir,
            DecimalByteUnit.MEGABYTES.toBytes(50).toInt().toLong()
        )

        OkHttpClient.Builder()
            .cache(cache)
            .build()
    }

    /**
     * Provide [Retrofit]
     */
    factory<Retrofit> {
        Retrofit.Builder()
            .client(get<OkHttpClient>())
            .baseUrl(BuildConfig.DARKSKY_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
            .build()
    }

    /**
     * Provide [WeatherService]
     */
    factory {
        get<Retrofit>().create(WeatherService::class.java)
    }
}