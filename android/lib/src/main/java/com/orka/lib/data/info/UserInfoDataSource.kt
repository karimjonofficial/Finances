package com.orka.lib.data.info

interface UserInfoDataSource {
    suspend fun insert(userData: UserInfo)
    suspend fun update(userData: UserInfo)
    suspend fun select(): UserInfo?
    suspend fun unauthorize()
}