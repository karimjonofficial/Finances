package com.orka.finances.lib.errors.data.repositories

object NullRepositoryError: RepositoryError {
    override fun getName(): String {
        return "NullRepositoryError"
    }
}