package com.orka.history

import com.orka.core.DoubleStateViewModel
import com.orka.core.HttpService
import com.orka.core.ReceiveDataSource
import com.orka.core.SaleDataSource
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

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
                            setPrimaryState(SaleContentState.Initialized(result.reversed().groupBy {
                                it.datetime.toLocalDateTime(TimeZone.currentSystemDefault()).date
                            }))
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
                            setSecondaryState(
                                ReceiveContentState.Initialized(
                                    result.reversed().groupBy {
                                        it.datetime.toLocalDateTime(TimeZone.currentSystemDefault()).date
                                    })
                            )
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