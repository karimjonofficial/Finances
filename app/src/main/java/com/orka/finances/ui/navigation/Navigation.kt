package com.orka.finances.ui.navigation

import kotlinx.serialization.Serializable

sealed class Navigation {
    @Serializable data object Login : Navigation()
    @Serializable data object Home : Navigation()
}