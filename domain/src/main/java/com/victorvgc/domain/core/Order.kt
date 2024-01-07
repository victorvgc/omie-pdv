package com.victorvgc.domain.core

import com.victorvgc.domain.utils.reduceTo
import java.math.BigDecimal

data class Order(
    val id: Long,
    val client: Client
) {
    private val _productList = mutableListOf<OrderProduct>()

    val productList: List<OrderProduct> = _productList

    val totalUnits: Long
        get() = productList.reduceTo { acc, next ->
            if (acc != null) {
                return@reduceTo acc + next.quantity
            }

            return@reduceTo next.quantity
        } ?: 0

    val totalPrice: BigDecimal
        get() = productList.reduceTo { acc, next ->
            if (acc != null) {
                return@reduceTo acc.plus(next.totalPrice)
            }

            return@reduceTo next.totalPrice
        } ?: BigDecimal.ZERO

    fun addProduct(product: Product, quantity: Long) {
        _productList.add(OrderProduct(product, quantity))
    }

    fun removeProduct(product: Product) {
        _productList.removeIf {
            it.product.name == product.name
        }
    }
}
