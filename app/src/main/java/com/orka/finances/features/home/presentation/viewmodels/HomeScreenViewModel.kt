package com.orka.finances.features.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.models.Category
import com.orka.finances.lib.data.credentials.CredentialsDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HomeScreenViewModel(
    private val dataSource: CategoriesDataSource,
    private val credentialsDataSource: CredentialsDataSource,
    private val passScreen: (Int) -> Unit,
    private val unauthorize: suspend () -> Unit
) : ViewModel() {
    private val _uiState = MutableStateFlow(emptyList<Category>())
    val uiState: StateFlow<List<Category>> = _uiState

    fun fetchData() {
        viewModelScope.launch {
            try {
                val credentials = credentialsDataSource.get()
                _uiState.value = dataSource.get(credentials.token) ?: emptyList()
            } catch(e: Exception) {
                when(e) {
                    is HttpException -> {
                        if (e.code() == 401) {
                            unauthorize()
                        }
                    }
                }
            }
        }
    }

    fun selectCategory(category: Category) {
        passScreen(category.id)
    }
}