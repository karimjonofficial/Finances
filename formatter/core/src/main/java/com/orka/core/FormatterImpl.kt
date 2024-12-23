package com.orka.core

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

class FormatterImpl : Formatter {
    override fun formatCurrency(value: Double, currencyName: String): String {
        return value.toString()
    }

    override fun formatTime(datetime: Instant): String {
        return datetime.toString()
    }

    override fun formatDate(date: LocalDate): String {
        return date.toString()
    }
}