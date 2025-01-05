package com.orka.login

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.components.AppScaffold

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginScreenViewModel
) {
    AppScaffold(modifier = modifier) { innerPadding ->
        LoginContent(
            modifier = Modifier.padding(innerPadding),
            viewModel = viewModel
        )
    }
}