package com.kennygunderman.ombrello.ui.forecast

import android.content.pm.PackageManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.kennygunderman.ombrello.R
import com.kennygunderman.ombrello.databinding.FragmentForecastBinding
import com.kennygunderman.ombrello.service.LOCATION_REQUEST
import com.kennygunderman.ombrello.service.LocationService
import com.kennygunderman.ombrello.ui.base.BaseFragment
import com.kennygunderman.ombrello.ui.forecast.adapter.ForecastAdapter
import com.kennygunderman.ombrello.util.LayoutUtil
import org.koin.android.ext.android.inject

class ForecastFragment : BaseFragment<ForecastViewModel, FragmentForecastBinding>(ForecastViewModel::class) {
    override val resId: Int
        get() = R.layout.fragment_forecast

    private val locationService by inject<LocationService>()

    override fun onStart() {
        super.onStart()
        setupViews()
        subscribeUi()

        locationService.callback = object : LocationService.Callback {
            override fun hasPermission(perm: String): Boolean =
                requireContext().checkSelfPermission(perm) == PackageManager.PERMISSION_GRANTED

            override fun permRequestNeeded(perm: String) {
                requestPermissions(arrayOf(perm), LOCATION_REQUEST)
            }
        }

        updateForecastFromLocation()
    }

    private fun setupViews() {
        binding.rvForecast.layoutManager = GridLayoutManager(
            context,
            LayoutUtil.calculateNoOfColumns(requireContext(), LayoutUtil.FORECAST_RECYCLER_ITEM_WIDTH)
        )
    }

    // Subscribe LiveData to UI Changes
    private fun subscribeUi() {
        viewModel.getHourlyForecast().observe(this, Observer { forecast ->
            binding.rvForecast.adapter = ForecastAdapter(forecast)
        })
    }

    private fun updateForecastFromLocation() {
        locationService.findLatLongFromLocation()?.let { loc ->
            viewModel.updateForecast(loc.first, loc.second)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            updateForecastFromLocation()
        }
    }
}