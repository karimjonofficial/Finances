package com.orka.finances.features.home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orka.finances.R
import com.orka.finances.features.home.models.Category

@Composable
fun CategoryButton(
    modifier: Modifier = Modifier,
    category: Category
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(
                    color = Color(0xFFECECEC)
                )
                .clickable {  }
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

        Spacer(Modifier.height(4.dp))

        Text(
            text = category.name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}