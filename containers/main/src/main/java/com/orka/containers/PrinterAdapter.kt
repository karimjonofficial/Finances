package com.orka.containers

import android.content.Context
import android.graphics.Bitmap
import androidx.print.PrintHelper
import com.orka.core.Printer

class PrinterAdapter(context: Context) : Printer {
    private val printerHelper = PrintHelper(context)

    override fun print(bitmap: Bitmap) {
        printerHelper.colorMode = PrintHelper.COLOR_MODE_MONOCHROME
        printerHelper.orientation = PrintHelper.ORIENTATION_PORTRAIT
        printerHelper.scaleMode = PrintHelper.SCALE_MODE_FIT
        printerHelper.printBitmap("Print", bitmap)
    }
}
