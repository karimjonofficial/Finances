package com.orka.finances.ui

import com.orka.finances.ID
import com.orka.finances.REFRESH
import com.orka.finances.TOKEN
import com.orka.finances.lib.data.info.UserInfo
import com.orka.finances.lib.data.info.UserInfoDataSource

class StubUserInfoDataSourceWithInfo : UserInfoDataSource {
    override suspend fun insert(userData: UserInfo) {
        TODO("Not yet implemented")
    }

    override suspend fun update(userData: UserInfo) {
        TODO("Not yet implemented")
    }

    override suspend fun select(): UserInfo {
        return UserInfo(ID, TOKEN, REFRESH)
    }

    override suspend fun unauthorize() {
        TODO("Not yet implemented")
    }

}
