package com.orka.history

import com.orka.history.models.ReceiveModel
import com.orka.history.models.SaleModel
import kotlinx.datetime.LocalDate

sealed class SaleContentState {
    data object Initial : SaleContentState()
    data object Offline : SaleContentState()
    data object Initializing : SaleContentState()
    data object Empty : SaleContentState()
    data class Initialized(val salesMap: Map<LocalDate, List<SaleModel>>) : SaleContentState()
}

sealed class ReceiveContentState {
    data object Initial : ReceiveContentState()
    data object Offline : ReceiveContentState()
    data object Initializing : ReceiveContentState()
    data object Empty : ReceiveContentState()
    data class Initialized(val receivesMap: Map<LocalDate, List<ReceiveModel>>) : ReceiveContentState()
}