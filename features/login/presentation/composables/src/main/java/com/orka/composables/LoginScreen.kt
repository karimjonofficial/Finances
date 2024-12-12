package com.orka.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.orka.lib.ui.AppScaffold
import com.orka.viewmodels.LoginScreenViewModel

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

@Preview
@Composable
private fun LoginScreenPreview() {
    Surface {
        LoginScreen(viewModel = LoginScreenViewModel(InMemoryLoginDataSource()) {})
    }
}