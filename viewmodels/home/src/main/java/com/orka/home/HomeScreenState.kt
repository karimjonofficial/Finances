package com.orka.home

import com.orka.categories.Category

sealed class HomeScreenState {
    data object Initial : HomeScreenState()
    data object Offline : HomeScreenState()
    data object Initializing : HomeScreenState()
    data object Empty : HomeScreenState()
    data class Initialized(val categories: List<Category>) : HomeScreenState()
}