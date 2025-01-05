package com.orka.containers

import com.orka.core.HttpService
import com.orka.http.HttpStatus
import retrofit2.HttpException

class HttpServiceImpl(private val unauthorize: () -> Unit) : HttpService {

    override suspend fun <T> invoke(
        onException: ((Exception) -> T)?,
        request: suspend () -> T
    ): T? {
        try { return request() } catch(e: Exception) {
            return onException?.invoke(e) ?:
            if(e.isUnauthorizedException()) {
                unauthorize()
                return null
            } else return null
        }
    }

    private fun Exception.isUnauthorizedException(): Boolean {
        return this is HttpException && this.code() == HttpStatus.Unauthorized.code
    }
}