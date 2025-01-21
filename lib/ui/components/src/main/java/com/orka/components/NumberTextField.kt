package com.orka.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.orka.core.CurrencyFormatter
import com.orka.input.CurrencyVisualTransformation
import com.orka.string.containsOnlyZeroes

@Composable
fun NumberTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    formatter: CurrencyFormatter,
    label: (@Composable () -> Unit)? = null,
    suffix: (@Composable () -> Unit)? = null
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = { if (!it.containsOnlyZeroes()) onValueChange(it) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        label = label,
        suffix = suffix,
        visualTransformation = CurrencyVisualTransformation(formatter)
    )
}

@Composable
fun NumberTextField(
    modifier: Modifier = Modifier,
    value: Int,
    onValueChange: (Int) -> Unit,
    formatter: CurrencyFormatter,
    label: (@Composable () -> Unit)? = null,
    suffix: (@Composable () -> Unit)? = null
) {
    OutlinedTextField(
        modifier = modifier,
        value = value.toString(),
        onValueChange = {
            val number = it.toIntOrNull()
            if (number != null) {
                if (number > 0) onValueChange(it.toInt())
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        label = label,
        suffix = suffix,
        visualTransformation = CurrencyVisualTransformation(formatter)
    )
}