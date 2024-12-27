package com.orka.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orka.log.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel(
    private val httpService: HttpService
) : ViewModel() {

    protected fun launch(context: CoroutineContext = Dispatchers.Default, f: suspend () -> Unit) {
        viewModelScope.launch(context = context) { f() }
    }

    protected fun request(
        onException: (Exception) -> Unit = {},
        request: suspend () -> Unit
    ) {
        launch(context = Dispatchers.IO) { httpService.invoke(request, onException) }
    }
}

abstract class SingleStateViewModel<T>(
    httpService: HttpService,
    initialState: T
) : BaseViewModel(httpService) {

    @Suppress("PropertyName")
    internal val _uiState: MutableStateFlow<T> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    protected open fun setState(state: T) {
        _uiState.value = state
        Log("${this::class.simpleName}", "State: $state")
    }
}

abstract class ListStateViewModel<T>(
    httpService: HttpService
) : SingleStateViewModel<List<T>>(
    httpService = httpService,
    initialState = emptyList()
) {
    override fun setState(state: List<T>) {
        _uiState.value = state
        Log("${this::class.simpleName}", "List size: ${state.size}")
    }
}

abstract class DoubleStateViewModel<Primary, Secondary>(
    httpService: HttpService,
    initialPrimaryState: Primary,
    initialSecondaryState: Secondary
) : BaseViewModel(httpService) {

    private val _primaryState: MutableStateFlow<Primary> = MutableStateFlow(initialPrimaryState)
    private val _secondaryState: MutableStateFlow<Secondary> = MutableStateFlow(initialSecondaryState)

    fun setPrimaryState(state: Primary) {
        _primaryState.value = state
        Log("${this::class.simpleName}", "Primary state: $state")
    }

    fun setSecondaryState(state: Secondary) {
        _secondaryState.value = state
        Log("${this::class.simpleName}", "Secondary state: $state")
    }

    protected val primaryState: StateFlow<Primary> = _primaryState.asStateFlow()
    protected val secondaryState: StateFlow<Secondary> = _secondaryState.asStateFlow()
}