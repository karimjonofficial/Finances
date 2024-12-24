package com.orka.string

fun String.removeSpaces(): String {
    return this.filter { !it.isWhitespace() }
}

fun String.convertFormattedString(): Double? {
    return "${this.removeSpaces()}.0".toDoubleOrNull()
}