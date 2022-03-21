package com.shahroz.twitterpg.extensions

import java.util.*

private fun currentDate(): Long {
    val calendar = Calendar.getInstance()
    return calendar.timeInMillis
}

// Long: time in millisecond

fun Long.toTimeAgo(): String {
    val currentTime = Date().time
    val timeDiff = currentTime - this
    when {
        timeDiff >= (1000 * 60 * 60 * 24) -> {
            // Days
            return "${timeDiff / (1000 * 60 * 60 * 24)}d"
        }
        timeDiff >= (1000 * 60 * 60) -> {
            // Hours
            return "${timeDiff / (1000 * 60 * 60)}h"
        }
        timeDiff >= (1000 * 60) -> {
            // Minutes
            return "${timeDiff / (1000 * 60)}m"
        }
        timeDiff >= 1000 -> {
            // Seconds
            return "${timeDiff / 1000}s"
        }
        else -> return "Just now"
    }
}