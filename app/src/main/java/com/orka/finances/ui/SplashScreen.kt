package com.orka.finances.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.main.MainViewModel
import com.orka.res.Strings

@Composable
internal fun SplashScreen(mainViewModel: MainViewModel) {
    Surface {

        Box(modifier = Modifier.fillMaxSize()) {

            Text(
                text = stringResource(Strings.app_is_initializing),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    mainViewModel.initUserInfo()
}