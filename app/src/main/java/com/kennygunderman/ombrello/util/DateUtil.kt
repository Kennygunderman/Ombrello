package com.kennygunderman.ombrello.util

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

interface IDateUtil {
    fun isToday(time: Long): Boolean
}
class DateUtil: IDateUtil {
    companion object {
        /**
         * formatter for simplifying forecast dates
         */
        val formatter = SimpleDateFormat("h:mm a", Locale.US)
    }

    override fun isToday(time: Long): Boolean {
        return DateUtils.isToday(time)
    }
}