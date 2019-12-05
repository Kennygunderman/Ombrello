package com.kennygunderman.ombrello.ui.forecast

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kennygunderman.ombrello.data.model.ForecastCondition
import com.kennygunderman.ombrello.data.model.HourlyResponse
import com.kennygunderman.ombrello.data.model.WeatherResponse
import com.kennygunderman.ombrello.mocks.MockWeatherServiceMockSuccess
import com.kennygunderman.ombrello.service.LocationService
import com.kennygunderman.ombrello.service.api.WeatherService
import com.kennygunderman.ombrello.util.DateUtil
import com.kennygunderman.ombrello.util.IDateUtil
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import java.util.*

class ForecastViewModelTest: KoinTest {

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    private val mockCondition = ForecastCondition("Cloudy", null, 88.0, Date())
    private val mockHourlyResponse = HourlyResponse(
        listOf(
            ForecastCondition("Cloudy", null, 45.0, Date()),
            ForecastCondition("Sunny", null, 77.0, Date()),
            ForecastCondition("Rain", null, 20.0, Date(-1)) //should NOT be in response
        )
    )
    private val mockResponse = WeatherResponse(mockCondition, mockHourlyResponse)

    /**
     * Create mock dependencies for viewModel
     *
     * The viewModel contains 3 dependencies
     *  - WeatherService
     *  - DateUtil
     *  - LocationService
     */
    private val mockModule = module {
        factory<LocationService> {
            declareMock {
                whenever(findLatLongFromLocation()).doReturn(Pair(10.0, 10.0))
            }
        }
        factory<WeatherService> { MockWeatherServiceMockSuccess(mockResponse) }
        factory<IDateUtil> {
            object : IDateUtil {
                override fun isToday(time: Long): Boolean {
                    return time != -1L
                }
            }
        }

        viewModel { ForecastViewModel(get(), get(), get()) }
    }

    private val viewModel by inject<ForecastViewModel>()

    @Before
    fun setUp() {
        startKoin {
            modules(mockModule)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    /**
     * Test's a successful call to get the weather forecast.
     *
     * expected output from this test:
     * ViewModel's temp should be updated correctly
     */

    @Test
    fun updateForecast_tempUpdated() {
        viewModel.updateForecastFromLocation()
        assertEquals("${mockCondition.temp.toInt()}°F", viewModel.getTemp().value)
    }

    /**
     * Test's a successful call to get the weather forecast.
     *
     * expected output from this test:
     * ViewModel's status should be updated
     */
    @Test
    fun updateForecast_summaryUpdated() {
        viewModel.updateForecastFromLocation()
        assertEquals(mockCondition.summary, viewModel.getStatus().value)
    }

    /**
     * Test's a successful call to get the weather forecast.
     *
     * expected output from this test:
     * ViewModel's Hourly Forecast should only contain Today's dates based on logic from [DateUtil]
     */
    @Test
    fun updateForecast_hourlyForecastParsed() {
        viewModel.updateForecastFromLocation()
        assertEquals(2, viewModel.getHourlyForecast().value?.size)
        assertEquals(mockHourlyResponse.hours[0], viewModel.getHourlyForecast().value!![0])
        assertEquals(mockHourlyResponse.hours[1], viewModel.getHourlyForecast().value!![1])
    }
}