package com.victorvgc.domain.core

import java.math.BigDecimal

data class OrderProduct(
    val product: Product,
    val quantity: Long
) {
    val totalPrice: BigDecimal
        get() = product.unitPrice.times(BigDecimal(quantity))
}
