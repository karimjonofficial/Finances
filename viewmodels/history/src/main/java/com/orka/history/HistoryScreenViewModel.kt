package com.orka.history

import com.orka.core.BaseViewModelWithFetch
import com.orka.core.HttpService
import com.orka.core.ReceiveDataSource
import com.orka.log.Log
import com.orka.receive.Receive

class HistoryScreenViewModel(
    private val dataSource: ReceiveDataSource,
    httpService: HttpService
) : BaseViewModelWithFetch<List<Receive>>(emptyList(), httpService) {

    override fun fetch() = invoke(
        request = { setState(dataSource.get() ?: emptyList()) },
        onException = { Log("HistoryScreenViewModel.Http", it.message ?: "No message") }
    )
}