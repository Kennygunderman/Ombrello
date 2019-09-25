package com.kennygunderman.ombrello.ui.forecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kennygunderman.ombrello.ui.base.BaseViewModel

class ForecastViewModel: BaseViewModel() {
    private val temp = MutableLiveData<String>()
    private val status = MutableLiveData<String>()


    fun getTemp(): LiveData<String> = temp
    fun getStatus(): LiveData<String> = status
}

