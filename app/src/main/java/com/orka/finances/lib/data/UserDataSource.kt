package com.orka.finances.lib.data

interface UserDataSource {
    suspend fun insert(userData: UserData)
    suspend fun update(userData: UserData)
    suspend fun getUserData(): UserData?
}