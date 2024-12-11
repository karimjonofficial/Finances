package com.orka.lib

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.BufferedSource

object NullResponseBody : ResponseBody() {
    override fun contentLength(): Long {
        return 0
    }

    override fun contentType(): MediaType? {
        return null
    }

    override fun source(): BufferedSource {
        TODO("Not implemented yet")
    }
}