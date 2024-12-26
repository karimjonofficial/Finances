package com.orka.history

import com.orka.core.DoubleStateViewModel
import com.orka.core.HttpService
import com.orka.core.ReceiveDataSource
import com.orka.core.SaleDataSource
import com.orka.log.Log
import com.orka.receive.Receive
import com.orka.sale.Sale
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class HistoryScreenViewModel(
    private val receiveDataSource: ReceiveDataSource,
    private val saleDataSource: SaleDataSource,
    httpService: HttpService
) : DoubleStateViewModel<Map<LocalDate, List<Receive>>, Map<LocalDate, List<Sale>>>(
    httpService = httpService,
    initialPrimaryState = emptyMap(),
    initialSecondaryState = emptyMap()
) {

    val receiveUiState = primaryState
    val saleUiState = secondaryState

    fun fetch() {
        launch(Dispatchers.Default) {
            request(
                request = {
                    val primary = receiveDataSource.get()?.reversed()?.groupBy {
                        it.datetime.toLocalDateTime(TimeZone.currentSystemDefault()).date
                    } ?: emptyMap()

                    setPrimaryState(primary)
                },
                onException = { Log("HistoryScreenViewModel.Http", it.message ?: "No message") }
            )
        }

        launch(Dispatchers.Default) {
            request(
                request = {
                    val state = saleDataSource.get()?.reversed()?.groupBy {
                        it.datetime.toLocalDateTime(TimeZone.currentSystemDefault()).date
                    } ?: emptyMap()

                    setSecondaryState(state)
                },
                onException = { Log("HistoryScreenViewModel.Http", it.message ?: "No message") }
            )
        }
    }
}