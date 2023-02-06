package codeasus.projects.renaissance.util

import android.content.Context
import android.os.Build
import android.text.format.DateUtils
import codeasus.projects.renaissance.R
import java.text.SimpleDateFormat
import java.util.*


private fun getCurrentLocale(context: Context): Locale {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        context.resources.configuration.locales[0]
    } else {
        context.resources.configuration.locale
    }
}

object DateTimePatterns {
    private const val BASE_DATE = "dd MMM"

    const val BASE_TIME = "HH:mm"
    const val FULL_DATE_TIME = "$BASE_DATE $BASE_TIME"
    const val NOT_WITHIN_CURRENT_WEEK_DATE_TIME = BASE_DATE
    const val PREVIOUS_YEARS_DATE_TIME = "$BASE_DATE yyyy"

    const val FULL_DEBUG_DATE_TIME = "dd.MM.yyyy $BASE_TIME.sss"
}

fun Date.formatAsDebugTime(context: Context): String {
    return SimpleDateFormat(
        DateTimePatterns.FULL_DEBUG_DATE_TIME,
        getCurrentLocale(context)
    ).format(this)
}

fun Date.formatAsLastSeen(context: Context): String {
    return "${context.getString(R.string.last_seen)} " + when {
        isToday() -> {
            SimpleDateFormat(
                DateTimePatterns.BASE_TIME,
                getCurrentLocale(context)
            ).format(this)
        }
        isYesterday() -> {
            "${context.getString(R.string.yesterday).lowercase()} ${
                SimpleDateFormat(
                    DateTimePatterns.BASE_TIME,
                    getCurrentLocale(context)
                ).format(this)
            }"
        }
        isThisWeek() -> {
            SimpleDateFormat(
                DateTimePatterns.FULL_DATE_TIME,
                getCurrentLocale(context)
            ).format(this)
        }
        isThisYear() -> {
            SimpleDateFormat(
                DateTimePatterns.NOT_WITHIN_CURRENT_WEEK_DATE_TIME,
                getCurrentLocale(context)
            ).format(this)
        }
        else -> {
            SimpleDateFormat(
                DateTimePatterns.PREVIOUS_YEARS_DATE_TIME,
                getCurrentLocale(context)
            ).format(this)
        }
    }
}

fun Date.isThisWeek(): Boolean {
    val thisCalendar = Calendar.getInstance()
    val thisWeek = thisCalendar.get(Calendar.WEEK_OF_YEAR)
    val thisYear = thisCalendar.get(Calendar.YEAR)

    val calendar = Calendar.getInstance()
    calendar.time = this
    val week = calendar.get(Calendar.WEEK_OF_YEAR)
    val year = calendar.get(Calendar.YEAR)

    return year == thisYear && week == thisWeek
}

fun Date.isToday(): Boolean {
    return DateUtils.isToday(this.time)
}

fun Date.isYesterday(): Boolean {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, -1)

    return calendar.get(Calendar.YEAR) == this.calendar.get(Calendar.YEAR)
            && calendar.get(Calendar.DAY_OF_YEAR) == this.calendar.get(Calendar.DAY_OF_YEAR)
}

fun Date.isThisYear(): Boolean {
    return Calendar.getInstance().get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
}

val Date.calendar: Calendar
    get() {
        val cal = Calendar.getInstance()
        cal.time = this
        return cal
    }