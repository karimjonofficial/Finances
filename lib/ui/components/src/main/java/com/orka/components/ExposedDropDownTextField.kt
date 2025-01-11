package com.orka.components

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.res.Strings

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun <T> ExposedDropDownTextField(
    text: String,
    menuExpanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    onDismissRequested: () -> Unit,
    items: List<T>?,
    itemText: (T) -> String,
    onItemSelected: (T) -> Unit
) {

    ExposedDropdownMenuBox(
        expanded = menuExpanded,
        onExpandedChange = { onExpandChange(it) }
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {},
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
            readOnly = true,
            label = { Text(stringResource(Strings.product)) },
            singleLine = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuExpanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )

        ExposedDropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = onDismissRequested
        ) {
            items?.forEach { item ->
                DropdownMenuItem(
                    text = { Text(itemText(item)) },
                    onClick = {
                        onItemSelected(item)
                        onDismissRequested()
                    }
                )
            }
        }
    }
}