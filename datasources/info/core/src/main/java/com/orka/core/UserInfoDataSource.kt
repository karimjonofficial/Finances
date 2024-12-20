package com.orka.core

import com.orka.info.UserInfo

interface UserInfoDataSource {
    suspend fun add(userData: UserInfo)
    suspend fun update(userData: UserInfo)
    suspend fun get(): UserInfo?
    suspend fun unauthorize()
}