package com.orka.input

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.orka.core.Formatter
import com.orka.log.Log
import com.orka.string.convertFormattedString

class CurrencyVisualTransformation(private val formatter: Formatter) : VisualTransformation {
    private val currencyOffsetMapping = CurrencyOffsetMapping()

    override fun filter(text: AnnotatedString): TransformedText {
        if (text.text != "") {

            val double = text.text.convertFormattedString()

            if (double != null) {

                if(!(double == 0.0 && text.length > 1)) {

                    return TransformedText(
                        AnnotatedString(formatter.formatCurrency(double)),
                        currencyOffsetMapping
                    )
                } else {

                    val builder = StringBuilder()

                    text.text.reversed().forEachIndexed { index, char ->
                        builder.append(char)
                        if((index + 1) % 3 == 0) { builder.append(" ") }
                    }

                    val string = builder.toString().reversed()
                    Log("CurrencyVisualTransformation", "Zeroes.length: ${string.length}")
                    return TransformedText(AnnotatedString(string), currencyOffsetMapping)
                }
            } else {
                return TransformedText(AnnotatedString("0"), OffsetMapping.Identity)
            }
        } else return TransformedText(text, OffsetMapping.Identity)
    }
}

class CurrencyOffsetMapping : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        Log("CurrencyOffsetMapping.oToT", "Offset: $offset")
        return (offset + ((offset - 1) / 3)).apply {
            Log(
                "CurrencyOffsetMapping.oToT",
                "Return: $this"
            )
        }
    }

    override fun transformedToOriginal(offset: Int): Int {
        Log("CurrencyOffsetMapping.tToO", "Offset: $offset")
        return (offset - ((offset - 1) / 3)).apply {
            Log(
                "CurrencyOffsetMapping.tToO",
                "Return: $this"
            )
        }
    }
}