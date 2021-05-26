package com.kennygunderman.ombrello

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.kennygunderman.ombrello.data.model.ForecastCondition
import com.kennygunderman.ombrello.service.LocationService
import com.kennygunderman.ombrello.service.api.WeatherService
import com.kennygunderman.ombrello.ui.forecast.ForecastViewModel
import com.kennygunderman.ombrello.util.IDateUtil
import com.nhaarman.mockito_kotlin.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.mock.declareMock
import org.mockito.Mockito
import java.util.*

@RunWith(AndroidJUnit4::class)
class ForecastTest: KoinTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java, true, false)

    private lateinit var mockVm: ForecastViewModel

    private val testErrorLiveData = MutableLiveData<String>()
    private val testForecastLiveData = MutableLiveData<List<ForecastCondition>>()

    private val mockModules = listOf(
        module {
            viewModel(override = true) {
                mockVm = declareMock {
                    given(locationService). will { Mockito.mock(LocationService::class.java) }
                    given(getWeatherError()).will { testErrorLiveData }
                    given(getHourlyForecast()).will { testForecastLiveData }
                }

                mockVm
            }
        }
    )

    @Before
    fun setUp() {
        loadKoinModules(mockModules)
        activityRule.launchActivity(Intent())
    }

    @After

    fun stop() {
        unloadKoinModules(mockModules)
    }

    /**
     * Test's that the Forecast adapter was updated correctly with the data provided
     */
    @Test
    fun forecastDisplayed() {
        val forecastData = listOf(
            ForecastCondition("Sunny", null, 400.0, Date()),
            ForecastCondition("Cloudy", null, 500.0, Date()),
            ForecastCondition("Cloudy", null, 600.0, Date()),
            ForecastCondition("Cloudy", null, 700.0, Date())
        )

        testForecastLiveData.postValue(forecastData)

        onView(withText("400°F")).check(matches(isDisplayed()))
        onView(withText("500°F")).check(matches(isDisplayed()))
        onView(withText("600°F")).check(matches(isDisplayed()))
        onView(withText("700°F")).check(matches(isDisplayed()))

        Thread.sleep(10000) //Added in for demo purposes
    }

    /**
     * Test's that the Error dialog was displayed when triggered
     * with the correct Error Message.
     */
    @Test
    fun errorDialogDisplayed() {
        val errorMessage = "UI Test Error Msg!"
        testErrorLiveData.postValue(errorMessage)

        onView(withText("Error")).check(matches(isDisplayed()))
        onView(withText(errorMessage)).check(matches(isDisplayed()))

        Thread.sleep(10000) //Added in for demo purposes
    }
}