package com.orka.finances.features.home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.orka.finances.R
import com.orka.finances.features.home.models.Category
import com.orka.finances.lib.components.HorizontalSpacer

@Composable
fun CategoryButton(
    modifier: Modifier = Modifier,
    category: Category,
    click: (Category) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(color = Color(0xFFECECEC))
                .clickable { click(category) }
        ) {
            Image(
                painter = painterResource(id = convertToImage(category.iconName) ?: R.drawable.chair),
                contentDescription = category.name,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(28.dp)
                    .clip(CircleShape)
            )
        }

        HorizontalSpacer(8)

        Text(
            text = category.name,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}