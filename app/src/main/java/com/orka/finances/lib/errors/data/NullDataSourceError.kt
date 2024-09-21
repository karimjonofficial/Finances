package com.orka.finances.lib.errors.data

object NullDataSourceError: DataSourceError {
    override fun getDescription(): String {
        return "NullDataSourceError"
    }
}