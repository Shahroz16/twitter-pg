package com.shahroz.twitterpg.extensions

import java.text.DateFormatSymbols
import java.util.*
import java.util.Calendar.*

private fun currentDate(): Long {
    val calendar = Calendar.getInstance()
    return calendar.timeInMillis
}

// Long: time in millisecond
fun Long.toTimeAgo(): String {
    val time = this
    val now = currentDate()

    // convert back to second
    val diff = (now - time) / 1000

    val month = DateFormatSymbols(Locale.ENGLISH).months[MONTH]
    return when {
        diff < MINUTE -> "Just now"
        diff < 2 * MINUTE -> "1m"
        diff < 60 * MINUTE -> "${diff / MINUTE}m "
        diff < 2 * HOUR -> "1h"
        diff < 24 * HOUR -> "${diff / HOUR}h"
        diff < 2 * DAY_OF_WEEK -> "1d"
        diff < 30 * DAY_OF_WEEK -> "${diff / DAY_OF_WEEK}d"
        else -> "$DAY_OF_MONTH $month"
    }
}