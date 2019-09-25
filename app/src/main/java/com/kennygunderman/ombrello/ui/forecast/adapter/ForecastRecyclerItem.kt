package com.kennygunderman.ombrello.ui.forecast.adapter

import com.kennygunderman.ombrello.data.model.ForecastCondition
import com.kennygunderman.ombrello.data.model.TempUnit
import com.kennygunderman.ombrello.util.DateUtil


class ForecastRecyclerItemViewModel(val forecast: ForecastCondition) {
    val time: String
        get() = DateUtil.formatter.format(forecast.time)

    val temp: String
        get() = "${forecast.temp.toInt()}${TempUnit.FAHRENHEIT.prefix()}"
}