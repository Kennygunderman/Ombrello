package com.kennygunderman.ombrello.mocks

import com.kennygunderman.ombrello.data.model.TempUnit
import com.kennygunderman.ombrello.data.model.WeatherResponse
import com.kennygunderman.ombrello.service.api.WeatherService
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MockWeatherServiceMockSuccess(val response: WeatherResponse): WeatherService {
    override fun getWeather(
        latitude: Double,
        longitude: Double,
        units: TempUnit
    ): Call<WeatherResponse> {
      return object: Call<WeatherResponse> {
          override fun enqueue(callback: Callback<WeatherResponse>) {
              callback.onResponse(this, Response.success(response))
          }

          override fun isExecuted(): Boolean {
              return true
          }

          override fun clone(): Call<WeatherResponse> {
              return this
          }

          override fun isCanceled(): Boolean {
              return false
          }

          override fun cancel() {

          }

          override fun execute(): Response<WeatherResponse> {
              return Response.success(response)
          }

          override fun request(): Request {
              return Request.Builder().build()
          }
      }
    }
}
