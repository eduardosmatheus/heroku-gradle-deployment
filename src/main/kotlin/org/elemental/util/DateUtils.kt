package org.elemental.util

import java.sql.Timestamp
import java.time.Instant
import java.time.ZoneId
import java.time.LocalDateTime
import java.util.*

object DateUtils {
    fun convertTimeStamp(param: String): String {
        return " (to_date('1970-01-01 00','yyyy-mm-dd hh24') + (:${param})/1000/60/60/24) "
    }

    fun convertLocalDateTimeToDate(dateToConvert: LocalDateTime): Date {
        return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant())
    }

    fun convertLocalDateTimeToTimestamp(date: LocalDateTime): Timestamp {
        return java.sql.Timestamp.from(date.atZone(ZoneId.systemDefault()).toInstant())
    }

    fun convertTimestampToLocalDateTime(date: Timestamp): LocalDateTime {
        return date.toLocalDateTime()
    }

    fun convertTimestampToDate(date: Timestamp): Date {
        return Date.from(date.toLocalDateTime().atZone(ZoneId.systemDefault()).toInstant())
    }

    fun convertInstantToTimestamp(date: Instant): Timestamp {
        return java.sql.Timestamp.from(date)
    }

    fun sysTimestamp(): Timestamp {
        val now = LocalDateTime.now()
        return convertLocalDateTimeToTimestamp(now)
    }

    fun sysDate(): Date {
        return convertTimestampToDate(sysTimestamp())
    }

}