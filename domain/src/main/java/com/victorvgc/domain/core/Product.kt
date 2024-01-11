package com.victorvgc.domain.core

import java.math.BigDecimal

data class Product(
    val name: String,
    val unitPrice: BigDecimal
) {

    val unitPriceWithoutFloatingPoint = unitPrice.times(BigDecimal(100)).toInt()

    companion object {
        val Empty = Product("", BigDecimal.ZERO)
    }
}
