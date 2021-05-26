package com.kennygunderman.ombrello.service.api

import com.kennygunderman.ombrello.BuildConfig
import com.kennygunderman.ombrello.data.model.TempUnit
import com.kennygunderman.ombrello.data.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {
    @GET("/forecast/${BuildConfig.DARKSKY_API_KEY}/{latitude},{longitude}")
    fun getWeather(
            @Path("latitude") latitude: Double,
            @Path("longitude") longitude: Double,
            @Query("units") units: TempUnit
    ): Call<WeatherResponse>
}