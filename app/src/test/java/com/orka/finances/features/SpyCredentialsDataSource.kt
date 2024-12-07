package com.orka.finances.features

import com.orka.finances.CREDENTIAL
import com.orka.finances.Counter
import com.orka.finances.lib.data.credentials.Credentials
import com.orka.finances.lib.data.credentials.CredentialsDataSource

class SpyCredentialsDataSource : CredentialsDataSource {
    val counter = Counter()

    override fun get(): Credentials {
        counter.count()
        return CREDENTIAL
    }

    override fun set(credentials: Credentials) {}
}