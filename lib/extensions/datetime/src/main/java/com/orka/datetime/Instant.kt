package com.orka.datetime

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

private val timeZone = TimeZone.currentSystemDefault()

fun Instant.toLocalTime(): LocalTime {
    return this.toLocalDateTime(timeZone).time
}

fun Instant.toLocalDate(): LocalDate {
    return this.toLocalDateTime(timeZone).date
}