package com.orka.history

import com.orka.core.BaseViewModelWithFetch
import com.orka.core.HttpService
import com.orka.core.ReceiveDataSource
import com.orka.core.SaleDataSource
import com.orka.log.Log
import com.orka.receive.Receive
import com.orka.sale.Sale
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class HistoryScreenViewModel(
    private val receiveDataSource: ReceiveDataSource,
    private val saleDataSource: SaleDataSource,
    httpService: HttpService
) : BaseViewModelWithFetch<Map<LocalDate, List<Receive>>>(emptyMap(), httpService) {

    private val _saleUiState: MutableStateFlow<Map<LocalDate, List<Sale>>> = MutableStateFlow(emptyMap())
    val saleUiState = _saleUiState.asStateFlow()

    override fun fetch() = invoke(

        request = {
            setState(
                receiveDataSource.get()?.reversed()?.groupBy {
                    it.datetime.toLocalDateTime(TimeZone.currentSystemDefault()).date
                } ?: emptyMap()
            )

            _saleUiState.value = saleDataSource.get()?.reversed()?.groupBy {
                it.datetime.toLocalDateTime(TimeZone.currentSystemDefault()).date
            } ?: emptyMap()
        },
        onException = { Log("HistoryScreenViewModel.Http", it.message ?: "No message") }
    )
}