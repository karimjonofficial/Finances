package com.orka.history

import com.orka.core.DoubleStateViewModel
import com.orka.core.HttpService
import com.orka.core.ReceiveDataSource
import com.orka.core.SaleDataSource
import com.orka.datetime.toLocalDate
import com.orka.history.models.ReceiveModel
import com.orka.history.models.SaleModel

class HistoryScreenViewModel(
    private val receiveDataSource: ReceiveDataSource,
    private val saleDataSource: SaleDataSource,
    httpService: HttpService
) : DoubleStateViewModel<SaleContentState, ReceiveContentState>(
    httpService = httpService,
    initialPrimaryState = SaleContentState.Initial,
    initialSecondaryState = ReceiveContentState.Initial
) {

    val saleUiState = primaryState
    val receiveUiState = secondaryState

    fun fetch() {
        fetchSales()
        fetchReceives()
    }

    private fun fetchSales() {
        launch {
            if (primaryState.value == SaleContentState.Initial)
                setPrimaryState(SaleContentState.Initializing)
            request {
                val result = saleDataSource.get()
                if (result != null) {
                    if (result.isNotEmpty()) {
                        launch {
                            val map = result.reversed().groupBy { it. datetime.toLocalDate() }
                            setPrimaryState(SaleContentState.Initialized(
                                salesMap = map.mapValues { it.value.map { sale -> SaleModel(sale) } }
                            ))
                        }
                    } else {
                        launch { setPrimaryState(SaleContentState.Empty) }
                    }
                } else {
                    launch { setPrimaryState(SaleContentState.Offline) }
                }
            }
        }
    }

    private fun fetchReceives() {
        launch {
            if (secondaryState.value == ReceiveContentState.Initial)
                setSecondaryState(ReceiveContentState.Initializing)
            request {
                val result = receiveDataSource.get()
                if (result != null) {
                    if (result.isNotEmpty()) {
                        launch {
                            val map = result.reversed().groupBy { it. datetime.toLocalDate() }
                            setSecondaryState(ReceiveContentState.Initialized(
                                receivesMap = map.mapValues { it.value.map { receive -> ReceiveModel(receive) } }
                            ))
                        }
                    } else {
                        launch { setSecondaryState(ReceiveContentState.Empty) }
                    }
                } else {
                    launch { setSecondaryState(ReceiveContentState.Offline) }
                }
            }
        }
    }
}