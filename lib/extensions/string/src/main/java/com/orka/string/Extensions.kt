package com.orka.string

fun String.removeSpaces(): String {
    return this.filter { !it.isWhitespace() }
}

fun String.convertFormattedString(): Double? {
    return "${this.removeSpaces()}.0".toDoubleOrNull()
}

fun String.containsOnlyZeroes(): Boolean {
    return this.toDoubleOrNull() == 0.0 && this.length > 1 && !this.contains('.')
}