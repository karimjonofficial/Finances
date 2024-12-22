package com.orka.core

interface Formatter {
    fun formatCurrency(value: Double, currencyName: String): String
}