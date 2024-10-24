package com.orka.finances.lib.errors.data.sources

data class UnknownDataSourceError(
    private val message: String = "Unknown error"
) : DataSourceError {
    override fun getDescription(): String {
        return message
    }
}