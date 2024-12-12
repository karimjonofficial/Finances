package com.orka.viewmodels

import com.orka.data.UserInfo
import com.orka.data.UserInfoDataSource

class StubUserInfoDataSourceWithNoInfo : UserInfoDataSource {
    override suspend fun add(userData: UserInfo) {
        TODO("Not yet implemented")
    }

    override suspend fun update(userData: UserInfo) {
        TODO("Not yet implemented")
    }

    override suspend fun get(): UserInfo? {
        return null
    }

    override suspend fun unauthorize() {
        TODO("Not yet implemented")
    }
}