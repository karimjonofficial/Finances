package com.orka.lib

import com.orka.finances.features.home.models.Category
import com.orka.finances.features.stock.models.Product
import com.orka.finances.features.stock.models.StockItem
import com.orka.finances.lib.data.credentials.Credential
import com.orka.finances.lib.resources.HttpStatus
import retrofit2.HttpException
import retrofit2.Response

const val ID = 1
const val USERNAME = "username"
const val PASSWORD = "password"
const val BAD_PASSWORD = "fake"
const val TOKEN = "token"
const val REFRESH = "access"
const val BLANK_LINE = "       "
const val NAME = "name"
const val DESCRIPTION = "description"
const val PRICE = 0.0
const val AMOUNT = 0

val UNAUTHORIZED_EXCEPTION = HttpException(Response.error<String>(HttpStatus.Unauthorized.code, NullResponseBody))
val HTTP_EXCEPTION = HttpException(Response.error<String>(501, NullResponseBody))

val CREDENTIAL = Credential(TOKEN, REFRESH)
val CATEGORY = Category(ID, NAME, DESCRIPTION)
val PRODUCT = Product(ID, NAME, PRICE, BLANK_LINE)
val STOCK_ITEM = StockItem(ID, PRODUCT, ID, AMOUNT)