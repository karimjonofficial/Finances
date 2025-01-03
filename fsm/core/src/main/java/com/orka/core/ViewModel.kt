package com.orka.core

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

typealias Base = androidx.lifecycle.ViewModel

abstract class ViewModel : Base() {

    protected fun launch(
        coroutineContext: CoroutineContext = Dispatchers.Default,
        callback: suspend () -> Unit
    ) = viewModelScope.launch(coroutineContext) { callback() }
}