package com.orka.finances.features.login.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.orka.finances.R
import com.orka.finances.features.login.presentation.viewmodel.LoginScreenViewModel
import com.orka.finances.lib.components.VerticalSpacer

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginScreenViewModel
) {
    Column(
        modifier = modifier.padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val username = rememberSaveable { mutableStateOf("") }
        val password = rememberSaveable { mutableStateOf("") }
        val remember = rememberSaveable { mutableStateOf(false) }
        val passwordVisible = rememberSaveable { mutableStateOf(false) }

        val visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()

        VerticalSpacer(32)

        Icon(
            modifier = Modifier.size(100.dp),
            painter = painterResource(R.drawable.logo),
            contentDescription = null
        )

        VerticalSpacer(16)

        Text(
            text = stringResource(R.string.enter_to_account),
            style = MaterialTheme.typography.headlineMedium
        )

        VerticalSpacer(16)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().height(60.dp),
            singleLine = true,
            value = username.value,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null
                )
            },
            onValueChange = { username.value = it },
            label = {
                Text(stringResource(R.string.username))
            }
        )

        VerticalSpacer(8)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().height(60.dp),
            singleLine = true,
            value = password.value,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = null
                )
            },
            visualTransformation = visualTransformation,
            trailingIcon = {
                IconButton(
                    onClick = { passwordVisible.value = !passwordVisible.value }
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (passwordVisible.value) R.drawable.visibility else R.drawable.visibility_off
                        ),
                        contentDescription = null
                    )
                }
            },
            onValueChange = { password.value = it },
            label = {
                Text(stringResource(R.string.password))
            }
        )

        VerticalSpacer(8)

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = remember.value, onCheckedChange = { remember.value = it })
            Spacer(Modifier.width(8.dp))
            Text(stringResource(R.string.remember_me))
        }

        VerticalSpacer(8)

        Button(
            modifier = Modifier.fillMaxWidth().height(58.dp),
            onClick = { viewModel.authorize(username.value, password.value) }
        ) {
            Text(
                text = stringResource(R.string.enter),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        VerticalSpacer(8)

        Text(stringResource(R.string.forgot_password))
    }
}