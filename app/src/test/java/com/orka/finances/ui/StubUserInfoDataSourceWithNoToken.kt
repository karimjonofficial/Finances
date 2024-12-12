package com.orka.finances.ui

import com.orka.data.UserInfo
import com.orka.data.UserInfoDataSource
import com.orka.lib.ID

class StubUserInfoDataSourceWithNoToken : UserInfoDataSource {
    override suspend fun add(userData: UserInfo) {
        TODO("Not yet implemented")
    }

    override suspend fun update(userData: UserInfo) {
        TODO("Not yet implemented")
    }

    override suspend fun get(): UserInfo {
        return UserInfo(id = ID, token = null, refresh = null)
    }

    override suspend fun unauthorize() {
        TODO("Not yet implemented")
    }
}