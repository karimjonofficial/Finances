package com.orka.core

import kotlin.Exception

interface HttpService {
    suspend fun <T> invoke(
        onException: ((Exception) -> T)? = null,
        request: suspend () -> T
    ): T?
}