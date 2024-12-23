package com.orka.uz

import com.orka.core.Formatter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.char
import java.text.DecimalFormat

class UzFormatter : Formatter {

    private val decimalFormat = DecimalFormat("#,###")

    private val timeFormat = DateTimeComponents.Format {
        //include date date(LocalDate.Formats.ISO)
        char(' ')
        hour(); char(':'); minute()
        //include minutes and seconds
//        optional {
//            char(':'); second()
//            optional {
//                char('.'); secondFraction(minLength = 3)
//            }
//        }
    }

    private val dateFormat = LocalDate.Format {
        date(LocalDate.Formats.ISO)
    }

    override fun formatCurrency(value: Double, currencyName: String): String {
        return "${decimalFormat.format(value).replace(",", " ")} $currencyName"
    }

    override fun formatTime(datetime: Instant): String {
        return datetime.format(timeFormat)
    }

    override fun formatDate(date: LocalDate): String {
        return date.format(dateFormat)
    }
}