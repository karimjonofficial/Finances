package com.orka.core

import com.orka.core.models.PostSaleRequestModel
import com.orka.core.models.PostSaleResponseModel
import com.orka.sale.Sale

interface SaleDataSource {

    suspend fun get(): List<Sale>?
    suspend fun add(body: PostSaleRequestModel): PostSaleResponseModel?
}