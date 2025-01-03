package com.orka.product

import androidx.lifecycle.viewModelScope
import com.orka.core.FsmState
import com.orka.core.UpdateProductModel
import com.orka.products.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

sealed class ProductScreenStates(internal open val id: Int) :
    FsmState<ProductScreenEvents, ProductScreenStates, ProductScreenViewModel>() {

    data class Initial(override val id: Int) : ProductScreenStates(id) {
        override suspend fun process(
            event: ProductScreenEvents,
            fsm: ProductScreenViewModel
        ): ProductScreenStates {
            return if (event is ProductScreenEvents.Init) Processing(id) {
                val product = fsm.viewModelScope.async(Dispatchers.IO) {
                    fsm.dataSource.get(id)
                }.await()

                Initialized(id, product)
            } else this
        }
    }

    @ConsistentCopyVisibility
    data class Processing internal constructor(
        override val id: Int,
        val callback: suspend () -> ProductScreenStates,
    ) : ProductScreenStates(id) {

        override suspend fun process(
            event: ProductScreenEvents,
            fsm: ProductScreenViewModel
        ): ProductScreenStates {
            return if(event is ProductScreenEvents.Process) {
                fsm.viewModelScope.async(Dispatchers.Default) { callback() }.await()
            } else Initial(id)
        }
    }

    data class Initialized(override val id: Int, val product: Product) : ProductScreenStates(id) {
        override suspend fun process(
            event: ProductScreenEvents,
            fsm: ProductScreenViewModel
        ): ProductScreenStates {
            return if (event is ProductScreenEvents.Edit) Editing(id, product) else Initial(id)
        }
    }

    data class Editing(override val id: Int, val product: Product) : ProductScreenStates(id) {

        override suspend fun process(
            event: ProductScreenEvents,
            fsm: ProductScreenViewModel
        ): ProductScreenStates {

            return if (event is ProductScreenEvents.Save) {
                Processing(id) {
                    fsm.dataSource.update(UpdateProductModel(id, event.name, event.price, event.description, product.categoryId))
                    event.reloadWarehouse(product.categoryId)
                    Initial(id)
                }
            } else Initial(id)
        }
    }
}

