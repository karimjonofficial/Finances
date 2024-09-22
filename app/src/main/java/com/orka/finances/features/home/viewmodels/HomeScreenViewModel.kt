package com.orka.finances.features.home.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeScreenViewModel : ViewModel() {
    private val _categories: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val categories: StateFlow<List<String>> = _categories

    fun addCategory(name: String) {
        val list = _categories.value.toMutableList()
        list.add(name)
        _categories.value = list
    }
}