package com.orka.main

import com.orka.credentials.Credential

sealed class MainEvent {
    data object Initialize : MainEvent()
    data class Authorize(val credential: Credential) : MainEvent()
    data object UnAuthorize : MainEvent()
    data class InitContainers(
        val navigateToWarehouse: (Int) -> Unit,
        val navigateToStockItem: (Int) -> Unit
    ) : MainEvent()
}