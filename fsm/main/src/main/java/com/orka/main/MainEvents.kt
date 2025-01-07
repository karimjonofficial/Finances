package com.orka.main

import com.orka.credentials.Credential

sealed class AppEvents {
    data object Initialize : AppEvents()
    data class Authorize(val credential: Credential) : AppEvents()
    data object UnAuthorize : AppEvents()
    data class InitContainers(
        val navigateToWarehouse: (Int) -> Unit,
        val navigateToStockProduct: (Int) -> Unit
    ) : AppEvents()
}