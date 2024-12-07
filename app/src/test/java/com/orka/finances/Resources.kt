package com.orka.finances

import com.orka.finances.features.home.models.Category
import com.orka.finances.lib.data.credentials.Credentials

const val ID = 1
const val USERNAME = "username"
const val PASSWORD = "password"
const val BAD_PASSWORD = "fake"
const val TOKEN = "token"
const val REFRESH = "access"
const val BLANK_LINE = "       "
const val UNAUTHORIZED_STATUS_CODE = 401
const val NAME = "name"
const val DESCRIPTION = "description"

val CREDENTIAL = Credentials(TOKEN, REFRESH)
val CATEGORY = Category(ID, NAME, DESCRIPTION)