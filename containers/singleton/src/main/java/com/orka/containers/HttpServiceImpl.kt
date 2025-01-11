package com.orka.containers

import android.util.Log
import com.orka.core.HttpService
import com.orka.http.HttpStatus
import retrofit2.HttpException

class HttpServiceImpl(private val unauthorize: () -> Unit) : HttpService {

    override suspend fun <T> invoke(
        onException: ((Exception) -> T)?,
        request: suspend () -> T
    ): T? {
        try { return request() } catch(e: Exception) {
            Log.d("HttpService", "Exception: ${e.message}")
            return onException?.invoke(e) ?:
            if(e.isUnauthorizedException()) {
                unauthorize()
                return null
            }
            else return null
        }
    }

    private fun Exception.isUnauthorizedException(): Boolean {
        return this is HttpException && this.code() == HttpStatus.Unauthorized.code
    }
}