package com.orka.input

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.orka.core.CurrencyFormatter

class CurrencyVisualTransformation(private val formatter: CurrencyFormatter) :
    VisualTransformation {
    private val zeroOffsetMapping = ZeroOffsetMapping()

    override fun filter(text: AnnotatedString): TransformedText {
        return if (text.text.isNotBlank()) filterNotBlankText(text)
        else TransformedText(text, OffsetMapping.Identity)
    }

    private fun filterNotBlankText(text: AnnotatedString): TransformedText {
        val currency = text.text.toDoubleOrNull()
        return if (currency != null) filterDoubleText(currency, text.text)
        else TransformedText(AnnotatedString(""), OffsetMapping.Identity)
    }

    private fun filterDoubleText(currency: Double, original: String): TransformedText {
        return if (currency == 0.0) TransformedText(AnnotatedString("0"), zeroOffsetMapping)
        else  {
            val formatted = formatter.formatCurrency(currency)
            TransformedText(
                text = AnnotatedString(formatted),
                offsetMapping = object : OffsetMapping {
                    override fun originalToTransformed(offset: Int): Int {
                        var transformedOffset = 0
                        var charsProcessed = 0

                        for (i in original.indices) {
                            if (charsProcessed == offset) break
                            if (i > 0 && (original.length - i) % 3 == 0) transformedOffset++
                            transformedOffset++
                            charsProcessed++
                        }

                        return transformedOffset
                    }

                    override fun transformedToOriginal(offset: Int): Int {
                        var originalOffset = 0
                        var charsProcessed = 0

                        for (i in formatted.indices) {
                            if (charsProcessed == offset) break
                            if (formatted[i] != ' ') originalOffset++
                            charsProcessed++
                        }

                        // Ensure the result is clamped within the original text range
                        return originalOffset.coerceIn(0, original.length)
                    }
                }
            )
        }
    }
}

class ZeroOffsetMapping : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        return 1
    }

    override fun transformedToOriginal(offset: Int): Int {
        return 1
    }
}