package com.orka.finances.features.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.models.Category
import com.orka.finances.lib.data.UserCredentialsDataSource
import com.orka.finances.lib.log.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val dataSource: CategoriesDataSource,
    private val credentialsSource: UserCredentialsDataSource,
    private val passScreen: (Int) -> Unit
) : ViewModel() {
    private val _uiState = MutableStateFlow(emptyList<Category>())
    val uiState: StateFlow<List<Category>> = _uiState

    private var count = 0

    init {
        count++
        log("init: $count")
        fetchData()
    }

    private fun fetchData() {
        val message = "fetchData"
        log(message)
        viewModelScope.launch {
            val credentials = credentialsSource.getCredentials()
            _uiState.value = dataSource.get(credentials!!.token) ?: emptyList()
        }
    }

    fun selectCategory(category: Category) {
        passScreen(category.id)
    }

    private fun log(message: String) {
        Log("HomeScreenViewModel", message)
    }
}