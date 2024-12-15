package com.orka.composables.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.base.Category
import com.orka.ui.HorizontalSpacer

@Composable
fun CategoryButton(
    modifier: Modifier = Modifier,
    category: Category,
    click: (Category) -> Unit
) {
    Row(
        modifier = modifier.clip(RoundedCornerShape(percent = 50)).clickable { click(category) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(color = Color(0xFFECECEC))
        ) {
            Image(
                imageVector = Icons.Filled.Star,
                contentDescription = category.name,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(28.dp)
                    .clip(CircleShape)
            )
        }

        HorizontalSpacer(16)

        Text(
            modifier = Modifier.weight(1f),
            text = category.name,
            style = MaterialTheme.typography.bodyMedium
        )

        HorizontalSpacer(8)
    }
}

@Preview
@Composable
private fun CategoryButtonPreview() {
    val category = Category(1, "Chair")

    Box(Modifier.fillMaxSize().background(Color.White).padding(horizontal = 32.dp)) {
        CategoryButton(
            modifier = Modifier.align(Alignment.Center).fillMaxWidth(),
            category = category,
            click = {}
        )
    }
}