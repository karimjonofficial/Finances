package com.orka.finances.lib.log

import android.util.Log

fun Log(tag: String, message: String) {
    Log.d(formatTag("FinancesApp", tag), message)
}

fun formatTag(outerTag: String, innerTag: String): String {
    return outerTag + if (innerTag.isNotBlank()) ".$innerTag" else ""
}