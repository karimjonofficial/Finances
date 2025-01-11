package com.orka.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.orka.res.Drawables
import com.orka.res.Strings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfflineScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    text: String = stringResource(Strings.no_connection_to_internet),
    retry: () -> Unit = {}
) {
    val refreshing = rememberSaveable { mutableStateOf(false) }

    PullToRefreshBox(
        isRefreshing = refreshing.value,
        onRefresh = {
            refreshing.value = true
            retry()
        }
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                painter = painterResource(Drawables.cloud_off),
                contentDescription = null
            )

            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}