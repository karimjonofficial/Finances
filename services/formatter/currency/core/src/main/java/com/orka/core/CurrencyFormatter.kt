package com.orka.core

interface CurrencyFormatter {
    fun formatCurrency(value: Double, currencyName: String): String
    fun formatCurrency(value: Double): String
}