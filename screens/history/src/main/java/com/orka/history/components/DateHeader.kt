package com.orka.history.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.orka.components.StickyHeader
import com.orka.core.Formatter
import kotlinx.datetime.LocalDate

@Composable
internal fun DateHeader(
    formatter: Formatter,
    date: LocalDate
) {
    StickyHeader {
        Text(
            text = formatter.formatDate(date),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}