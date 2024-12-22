package com.orka.core

class FormatterImpl : Formatter {
    override fun formatCurrency(value: Double, currencyName: String): String {
        return value.toString()
    }
}