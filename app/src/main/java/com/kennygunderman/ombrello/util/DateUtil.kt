package com.kennygunderman.ombrello.util

import java.text.SimpleDateFormat
import java.util.*


object DateUtil {
    /**
     * formatter for simplifying forecast dates
     */
    val formatter = SimpleDateFormat("h:mm a", Locale.US)
}