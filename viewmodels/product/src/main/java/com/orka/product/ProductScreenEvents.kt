package com.orka.product

import com.orka.core.FsmEvent

sealed class ProductScreenEvents : FsmEvent {
    data object Init : ProductScreenEvents()
    data object Edit : ProductScreenEvents()
    data object Process : ProductScreenEvents()

    data class Save(
        internal val name: String,
        internal val price: Double,
        internal val description: String,
        internal val reloadWarehouse: (Int) -> Unit
    ) : ProductScreenEvents()
}
