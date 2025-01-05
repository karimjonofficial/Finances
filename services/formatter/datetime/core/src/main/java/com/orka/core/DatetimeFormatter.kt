package com.orka.core

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

interface DatetimeFormatter {
    fun formatTime(time: LocalTime): String
    fun formatDate(date: LocalDate): String
}