package com.orka.core

import android.content.Context
import android.graphics.Bitmap
import androidx.print.PrintHelper

class Printer(context: Context) {
    private val printerHelper = PrintHelper(context)

    fun print(bitmap: Bitmap) {
        printerHelper.colorMode = PrintHelper.COLOR_MODE_MONOCHROME
        printerHelper.orientation = PrintHelper.ORIENTATION_PORTRAIT
        printerHelper.scaleMode = PrintHelper.SCALE_MODE_FIT
        printerHelper.printBitmap("Print", bitmap)
    }
}