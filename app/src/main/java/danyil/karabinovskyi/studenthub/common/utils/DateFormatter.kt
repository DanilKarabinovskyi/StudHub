package danyil.karabinovskyi.studenthub.common.utils

import android.content.Context
import android.text.format.DateFormat
import danyil.karabinovskyi.studenthub.R
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


public interface DateFormatter {

    public fun formatDate(localDateTime: LocalDateTime?): String

    public fun formatTime(localTime: LocalTime?): String

    public companion object {
        @JvmStatic
        public fun from(context: Context): DateFormatter = DefaultDateFormatter(context)
    }
}

public fun DateFormatter.formatDate(date: Date?): String {
    return formatDate(date?.let(DateConverter::toLocalDateTime))
}

public fun DateFormatter.formatTime(date: Date?): String {
    return formatTime(date?.let(DateConverter::toLocalTime))
}

internal class DefaultDateFormatter(
    private val dateContext: DateContext
) : DateFormatter {

    constructor(context: Context) : this(DefaultDateContext(context))

    private val timeFormatter12h = DateTimeFormatter.ofPattern("h:mm a")
    private val timeFormatter24h = DateTimeFormatter.ofPattern("HH:mm")
    private val dateFormatterDayOfWeek = DateTimeFormatter.ofPattern("EEEE")
    private val dateFormatterFullDate: DateTimeFormatter
        // Re-evaluated every time to account for runtime Locale changes
        get() = DateTimeFormatter.ofPattern(dateContext.dateTimePattern())

    override fun formatDate(localDateTime: LocalDateTime?): String {
        localDateTime ?: return ""

        val localDate = localDateTime.toLocalDate()
        return when {
            localDate.isToday() -> formatTime(localDateTime.toLocalTime())
            localDate.isYesterday() -> dateContext.yesterdayString()
            localDate.isWithinLastWeek() -> dateFormatterDayOfWeek.format(localDate)
            else -> dateFormatterFullDate.format(localDate)
        }
    }

    override fun formatTime(localTime: LocalTime?): String {
        localTime ?: return ""
        val formatter = if (dateContext.is24Hour()) timeFormatter24h else timeFormatter12h
        return formatter.format(localTime).uppercase(Locale.getDefault())
    }

    private fun LocalDate.isToday(): Boolean {
        return this == dateContext.now()
    }

    private fun LocalDate.isYesterday(): Boolean {
        return this == dateContext.now().minusDays(1)
    }

    private fun LocalDate.isWithinLastWeek(): Boolean {
        return this > dateContext.now().minusDays(DAYS_IN_A_WEEK)
    }

    interface DateContext {
        fun now(): LocalDate
        fun yesterdayString(): String
        fun is24Hour(): Boolean
        fun dateTimePattern(): String
    }

    private class DefaultDateContext(
        private val context: Context
    ) : DateContext {
        override fun now(): LocalDate = LocalDate.now()

        override fun yesterdayString(): String {
            return context.getString(R.string.yesterday)
        }

        override fun is24Hour(): Boolean {
            return DateFormat.is24HourFormat(context)
        }

        override fun dateTimePattern(): String {
            // Gets a localized pattern that contains 2 digit representations of
            // the year, month, and day of month
            return DateFormat.getBestDateTimePattern(Locale.getDefault(), "yy MM dd")
        }
    }
}

private const val DAYS_IN_A_WEEK = 7L
