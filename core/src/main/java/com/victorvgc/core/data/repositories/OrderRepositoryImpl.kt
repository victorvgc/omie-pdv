package com.victorvgc.core.data.repositories

import com.victorvgc.core.domain.data_sources.OrderDataSource
import com.victorvgc.core.domain.repositories.OrderRepository
import com.victorvgc.domain.core.Order
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDataSource: OrderDataSource
) : OrderRepository {
    override suspend fun createOrder(order: Order) = orderDataSource.createOrder(order)

    override suspend fun updateOrder(order: Order) = orderDataSource.updateOrder(order)

    override suspend fun deleteOrder(order: Order) = orderDataSource.deleteOrder(order)

    override suspend fun getAllOrders() = orderDataSource.getAllOrders()

    override suspend fun getOrder(orderId: Long) = orderDataSource.getOrder(orderId)
    override suspend fun getHighestOrderId() = orderDataSource.getHighestOrderId()

    override suspend fun getOrdersFromTo(from: Long, to: Long) =
        orderDataSource.getOrdersFromTo(from, to)
}
