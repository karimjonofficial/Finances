package com.orka.core

import kotlin.Exception

interface HttpService {

    suspend fun invoke(request: suspend () -> Unit)
    suspend fun invokeOnly(request: suspend () -> Unit)

    suspend fun invoke(
        request: suspend () -> Unit,
        onException: ((Exception) -> Unit)?
    )

    suspend fun invoke(
        request: suspend () -> Unit,
        onException: ((Exception) -> Unit)?,
        onUnauthorize: (() -> Unit)?
    )

    suspend fun invoke(
        request: suspend () -> Unit,
        onUnauthorize: (() -> Unit)?
    )
}