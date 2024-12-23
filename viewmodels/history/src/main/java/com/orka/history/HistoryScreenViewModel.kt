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

class HistoryScreenViewModel(
    private val receiveDataSource: ReceiveDataSource,
    private val saleDataSource: SaleDataSource,
    httpService: HttpService
) : BaseViewModelWithFetch<List<Receive>>(emptyList(), httpService) {

    private val _saleUiState: MutableStateFlow<List<Sale>> = MutableStateFlow(emptyList())
    val saleUiState = _saleUiState.asStateFlow()

    override fun fetch() = invoke(
        request = {
            setState(receiveDataSource.get() ?: emptyList())
            _saleUiState.value = saleDataSource.get() ?: emptyList()
        },
        onException = { Log("HistoryScreenViewModel.Http", it.message ?: "No message") }
    )
}