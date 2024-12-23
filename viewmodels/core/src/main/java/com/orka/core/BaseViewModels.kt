package com.orka.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orka.log.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel : ViewModel() {
    protected fun launch(context: CoroutineContext = EmptyCoroutineContext, f: suspend () -> Unit) {
        viewModelScope.launch(context = context) { f() }
    }
}

abstract class BaseViewModelWithState<T>(
    initialState: T
) : BaseViewModel() {
    private val _uiState: MutableStateFlow<T> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    protected fun setState(value: T) {
        Log("${this::class.simpleName}.State", "${value!!::class.simpleName}: $value")
        _uiState.value = value
    }
}

abstract class BaseViewModelWithInvoke<T>(
    initialState: T,
    private val httpService: HttpService
) : BaseViewModelWithState<T>(initialState) {

    protected fun invoke(
        request: suspend () -> Unit,
        onException: (Exception) -> Unit
    ) {
        launch(context = Dispatchers.IO) { httpService.invoke(request, onException) }
    }

    protected fun invoke(request: suspend () -> Unit) {
        launch(context = Dispatchers.IO) { httpService.invoke { request() } }
    }
}

abstract class BaseViewModelWithFetch<T>(
    initialState: T,
    httpService: HttpService
) : BaseViewModelWithInvoke<T>(initialState, httpService) {

    abstract fun fetch()
}

