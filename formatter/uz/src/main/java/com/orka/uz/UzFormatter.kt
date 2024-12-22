package com.orka.uz

import com.orka.core.Formatter
import java.text.DecimalFormat

class UzFormatter : Formatter {

    private val format = DecimalFormat("#,###")

    override fun formatCurrency(value: Double, currencyName: String): String {
        return "${format.format(value).replace(",", " ")} $currencyName"
    }
}