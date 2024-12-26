package com.orka.main

import com.orka.core.HttpService

object NullHttpService : HttpService {
    override suspend fun invoke(request: suspend () -> Unit) {

    }

    override suspend fun invoke(request: suspend () -> Unit, onException: ((Exception) -> Unit)?) {

    }

    override suspend fun invoke(
        request: suspend () -> Unit,
        onException: ((Exception) -> Unit)?,
        onUnauthorize: (() -> Unit)?
    ) {

    }

    override suspend fun invoke(request: suspend () -> Unit, onUnauthorize: (() -> Unit)?) {

    }

    override suspend fun invokeOnly(request: suspend () -> Unit) {

    }
}