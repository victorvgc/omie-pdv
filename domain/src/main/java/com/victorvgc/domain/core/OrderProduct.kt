package com.victorvgc.domain.core

import java.math.BigDecimal

data class OrderProduct(
    val product: Product,
    val quantity: Long
) {

    companion object {
        val Empty = OrderProduct(product = Product.Empty, 0)
    }

    val totalPrice: BigDecimal
        get() = product.unitPrice.times(BigDecimal(quantity))
}
