package com.victorvgc.domain.core

import java.math.BigDecimal
import java.util.Date

data class Order(
    val id: Long,
    val client: Client = Client.Empty,
    val productList: List<OrderProduct> = emptyList(),
    val createdAt: Long = Date().time
) {
    companion object {
        val Empty = Order(0)
    }

    val totalUnits: Long
        get() = productList.map { it.quantity }.takeIf { it.isNotEmpty() }?.reduce { acc, next ->
            acc + next
        } ?: 0

    val totalPrice: BigDecimal
        get() = productList.map { it.totalPrice }.takeIf { it.isNotEmpty() }?.reduce { acc, next ->
            acc + next
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
