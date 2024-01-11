package com.victorvgc.domain.core

import com.victorvgc.utils.extensions.reduceTo
import java.math.BigDecimal

data class Order(
    val id: Long,
    val client: Client = Client.Empty,
    val productList: List<OrderProduct> = emptyList()
) {
    companion object {
        val Empty = Order(0)
    }

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

    fun addProduct(product: Product, quantity: Long): Order {
        val mutableList = productList.toMutableList()
        mutableList.add(OrderProduct(product, quantity))

        return this.copy(productList = mutableList)
    }

    fun removeProduct(product: Product): Order {
        val mutableList = productList.toMutableList()
        mutableList.removeIf {
            it.product.name == product.name
        }

        return this.copy(productList = mutableList)
    }
}
