package com.orka.core

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

interface Formatter {
    fun formatCurrency(value: Double, currencyName: String): String
    fun formatCurrency(value: Double): String
    fun formatTime(datetime: Instant): String
    fun formatDate(date: LocalDate): String
}