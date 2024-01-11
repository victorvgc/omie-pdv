package com.victorvgc.order_screen.domain

import com.victorvgc.domain.core.Client
import com.victorvgc.domain.core.Order
import com.victorvgc.domain.core.OrderProduct
import com.victorvgc.domain.core.Product

data class OrderScreenState(
    val order: Order,
    val currentProduct: OrderProduct = OrderProduct.Empty,
    val enableContinue: Boolean = false,
    val enableAddProduct: Boolean = false,
    val showProductForm: Boolean = false,
    val isEdit: Boolean = false,
    val leaveScreen: Boolean = false,
    val showDiscardChangesDialog: Boolean = false,
    val currentStep: ScreenStep = ScreenStep.ADD_CLIENT,
    val clientsList: List<Client> = emptyList(),
    val productList: List<Product> = emptyList()
) {
    enum class ScreenStep {
        ADD_CLIENT, ADD_PRODUCTS, FINISH_ORDER
    }
}
