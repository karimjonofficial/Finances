package com.orka.login

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.orka.login.fixtures.CredentialsManagerImpl
import com.orka.login.fixtures.HttpServiceImpl
import com.orka.login.fixtures.InMemoryCredentialsDataSource
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

@Preview
@Composable
private fun LoginScreenPreview() {
    Surface {
        LoginScreen(
            viewModel = LoginScreenViewModel(
                dataSource = InMemoryCredentialsDataSource(),
                credentialsManager = CredentialsManagerImpl(),
                httpService = HttpServiceImpl()
            )
        )
    }
}