package com.orka.finances.features.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orka.finances.features.login.data.source.LoginDataSource
import com.orka.finances.lib.errors.data.sources.NullDataSourceError
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val dataSource: LoginDataSource,
    private val passScreen: () -> Unit
) : ViewModel() {

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val pair = dataSource.getCredentials(username, password)
            if (pair.second == NullDataSourceError) { passScreen() }
        }
    }
}