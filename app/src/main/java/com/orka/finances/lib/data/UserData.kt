package com.orka.finances.lib.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "info")
data class UserData(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val token: String,
    val refresh: String
)
