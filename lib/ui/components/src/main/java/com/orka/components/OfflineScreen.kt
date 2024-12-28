package com.orka.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.orka.res.Drawables

@Composable
fun OfflineScreen(modifier: Modifier = Modifier.fillMaxSize()) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(Drawables.cloud_off),
            contentDescription = null
        )
    }
}