package com.kennygunderman.ombrello.util

import android.content.Context

object LayoutUtil {
    const val FORECAST_RECYCLER_ITEM_WIDTH = 96F

    fun calculateNoOfColumns(
        context: Context,
        columnWidthDp: Float
    ): Int {
        val displayMetrics = context.resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return (screenWidthDp / columnWidthDp + 0.5).toInt()
    }
}