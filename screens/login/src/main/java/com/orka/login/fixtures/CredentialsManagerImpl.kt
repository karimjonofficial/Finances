package com.orka.login.fixtures

import com.orka.core.CredentialsManager
import com.orka.credentials.Credential

class CredentialsManagerImpl : CredentialsManager {
    override fun get(): Credential? {
        throw Exception()
    }

    override fun set(credential: Credential) {
        throw Exception()
    }

}
