package com.kennygunderman.ombrello.ui.forecast.adapter

import com.kennygunderman.ombrello.R
import com.kennygunderman.ombrello.data.model.ForecastCondition
import com.kennygunderman.ombrello.ui.base.BaseRecyclerViewAdapter

class ForecastAdapter(val forecast: List<ForecastCondition>) : BaseRecyclerViewAdapter() {

    override fun getObjForPosition(position: Int): Any {
        val fc = forecast[position]
        return ForecastRecyclerItemViewModel(fc)
    }

    override fun getLayoutIdForPosition(position: Int): Int = R.layout.recycler_item_forecast
    override fun getItemCount(): Int = forecast.size
}