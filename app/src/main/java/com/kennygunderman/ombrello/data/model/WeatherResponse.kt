package com.kennygunderman.ombrello.data.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(

    /**
     * Current Weather Condition
     * @return ForecastCondition
     */
    @SerializedName("currently")
    var currentForecast: ForecastCondition?,
    /**
     * Hourly Response model that contains list of ForecastConditions
     * @return HourlyResponse
     */
    var hourly: HourlyResponse?
)