package com.kennygunderman.ombrello.data.model

import com.google.gson.annotations.SerializedName

enum class TempUnit(private val value: String) {
    @SerializedName("si")
    CELSIUS("si"),

    @SerializedName("us")
    FAHRENHEIT("us");

    override fun toString(): String {
        return value
    }

    fun prefix(): String {
       return when (this) {
            CELSIUS -> "°C"
            FAHRENHEIT -> "°F"
        }
    }
}