package com.orka.input

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.orka.core.CurrencyFormatter

class CurrencyVisualTransformation(private val formatter: CurrencyFormatter) : VisualTransformation {
    private val currencyOffsetMapping = CurrencyOffsetMapping()
    private val zeroOffsetMapping = ZeroOffsetMapping()

    override fun filter(text: AnnotatedString): TransformedText {
        return if (text.text.isNotBlank()) {
            filterNotBlankText(text)
        } else {
            TransformedText(text, OffsetMapping.Identity)
        }
    }

    private fun filterNotBlankText(text: AnnotatedString): TransformedText {
        val currency = text.text.toDoubleOrNull()
        return if (currency != null) filterDoubleText(currency)
        else TransformedText(AnnotatedString(""), OffsetMapping.Identity)
    }

    private fun filterDoubleText(
        currency: Double
    ): TransformedText {
        return if(currency == 0.0) TransformedText(AnnotatedString("0"), zeroOffsetMapping)
        else TransformedText(AnnotatedString(formatter.formatCurrency(currency)), currencyOffsetMapping)
    }
}

class CurrencyOffsetMapping : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        return (offset + ((offset - 1) / 3))
    }

    override fun transformedToOriginal(offset: Int): Int {
        return (offset - ((offset - 1) / 3))
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