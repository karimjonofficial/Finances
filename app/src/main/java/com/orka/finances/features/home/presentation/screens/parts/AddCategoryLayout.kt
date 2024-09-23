package com.orka.finances.features.home.presentation.screens.parts

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.finances.lib.components.Spacer8

@Composable
internal fun AddCategoryLayout(
    text: String,
    onTextChanged: (String) -> Unit,
    addCategory: (String) -> Unit
) {
    Row(Modifier.padding(horizontal = 16.dp)) {
        TextField(
            value = text,
            label = { Text("Enter the name") },
            onValueChange = { onTextChanged(it) },
            modifier = Modifier.weight(1f)
        )

        Spacer8()

        Button(
            onClick = {
                addCategory(text)
                onTextChanged("")
            }
        ) {
            Text("Add")
        }
    }
}