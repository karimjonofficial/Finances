package com.orka.tests

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
        throw Exception()
    }
}