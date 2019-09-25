package com.kennygunderman.ombrello.ui.forecast

import com.kennygunderman.ombrello.R
import com.kennygunderman.ombrello.databinding.FragmentForecastBinding
import com.kennygunderman.ombrello.ui.base.BaseFragment

class ForecastFragment : BaseFragment<ForecastViewModel, FragmentForecastBinding>(ForecastViewModel::class) {
    override val resId: Int
        get() = R.layout.fragment_forecast

}