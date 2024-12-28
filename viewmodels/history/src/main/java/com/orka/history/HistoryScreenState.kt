package com.orka.history

import com.orka.receive.Receive
import com.orka.sale.Sale
import kotlinx.datetime.LocalDate

sealed class SaleContentState {
    data object Initial : SaleContentState()
    data object Offline : SaleContentState()
    data object Initializing : SaleContentState()
    data object Empty : SaleContentState()
    data class Initialized(val categories: Map<LocalDate, List<Sale>>) : SaleContentState()
}

sealed class ReceiveContentState {
    data object Initial : ReceiveContentState()
    data object Offline : ReceiveContentState()
    data object Initializing : ReceiveContentState()
    data object Empty : ReceiveContentState()
    data class Initialized(val categories: Map<LocalDate, List<Receive>>) : ReceiveContentState()
}