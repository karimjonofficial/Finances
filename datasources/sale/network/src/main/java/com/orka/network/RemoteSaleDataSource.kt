package com.orka.network

import com.orka.core.SaleDataSource
import com.orka.core.models.PostSaleRequestModel
import com.orka.core.models.PostSaleResponseModel
import com.orka.credentials.Credential
import com.orka.sale.Sale
import com.orka.sale.SaleItem
import retrofit2.Retrofit
import retrofit2.create

class RemoteSaleDataSource private constructor(
    private val apiService: SaleApiService,
    credential: Credential
) : SaleDataSource {

    private val authorizationHeader = "Bearer ${credential.token}"

    override suspend fun get(): List<Sale>? {
        return apiService.get(authorizationHeader)?.map {
            Sale(
                id = it.id,
                items = it.items.map { item ->
                    SaleItem(
                        id = item.id,
                        product = item.product,
                        amount = item.amount.toInt(),
                        saleId = item.saleId
                    )
                },
                price = it.price,
                datetime = it.datetime,
                comment = it.comment,
                userId = it.userId
            )
        }
    }

    override suspend fun add(body: PostSaleRequestModel): PostSaleResponseModel? {
        return apiService.post(authorizationHeader, body)
    }

    companion object {
        fun create(retrofit: Retrofit, credential: Credential): SaleDataSource {
            return RemoteSaleDataSource(retrofit.create<SaleApiService>(), credential)
        }
    }
}