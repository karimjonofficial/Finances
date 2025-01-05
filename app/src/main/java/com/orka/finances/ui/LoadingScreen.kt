package com.orka.finances.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.components.VerticalSpacer
import com.orka.res.Strings
import kotlinx.coroutines.delay

@Composable
internal fun LoadingScreen(
    modifier: Modifier = Modifier,
    initContainers: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(500)
        initContainers()
    }

    Surface(modifier = modifier) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(modifier = Modifier.weight(1f)) {

                Text(
                    text = stringResource(Strings.initializing),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            LinearProgressIndicator(
                modifier = Modifier.width(128.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            VerticalSpacer(64)
        }
    }
}

@Preview
@Composable
private fun LoadingScreenPreview() {
    LoadingScreen {}
}