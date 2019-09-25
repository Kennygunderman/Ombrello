package com.kennygunderman.ombrello.ui.forecast

import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kennygunderman.ombrello.data.model.ForecastCondition
import com.kennygunderman.ombrello.data.model.TempUnit
import com.kennygunderman.ombrello.data.model.WeatherResponse
import com.kennygunderman.ombrello.service.api.WeatherService
import com.kennygunderman.ombrello.ui.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastViewModel
constructor(private val weatherService: WeatherService): BaseViewModel() {
    private val temp = MutableLiveData<String>()
    private val status = MutableLiveData<String>()
    private val hourlyForecast = MutableLiveData<List<ForecastCondition>>()

    fun getTemp(): LiveData<String> = temp
    fun getStatus(): LiveData<String> = status
    fun getHourlyForecast(): LiveData<List<ForecastCondition>> = hourlyForecast

    /**
     * Update's forecast from the Latitude and Longitude provided
     *
     */
    fun updateForecast(lat: Double, long: Double) {
        weatherService.getWeather(lat, long, TempUnit.FAHRENHEIT)
            .enqueue(object: Callback<WeatherResponse> {
                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    //...

                    Log.d("Error", t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {
                    response.body()?.hourly?.hours?.let { hourlyForecast ->
                        parseHourlyForecast(hourlyForecast)
                    }

                    response.body()?.currentForecast?.let { currentForecast ->
                        updateStatus(currentForecast)
                    }
                }
            })
    }

    /**
     * parse through the hourly forecast response and create a new list of forecasts
     * that are for today's date.
     *
     * Posts updated value to observer
     *
     * @param hourlyForecast List of hour by hour ForecastCondition
     */
    private fun parseHourlyForecast(hourlyForecast: List<ForecastCondition>) {
        val hourlyToday = mutableListOf<ForecastCondition>()

        hourlyForecast.forEach { forecast ->
            if (DateUtils.isToday(forecast.time?.time ?: 0L)) {
                hourlyToday.add(forecast)
            }
        }

        this.hourlyForecast.value = hourlyToday
    }

    /**
     * Update's the status of the current temperature
     */
    private fun updateStatus(forecast: ForecastCondition) {
        temp.value = "${forecast.temp.toInt()}${TempUnit.FAHRENHEIT.prefix()}"
        status.value = forecast.summary ?: ""
    }
}

