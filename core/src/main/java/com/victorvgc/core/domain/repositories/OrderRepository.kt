package com.victorvgc.core.domain.repositories

import com.victorvgc.domain.core.Order
import com.victorvgc.utils.result_helper.ResultWrapper

interface OrderRepository {
    suspend fun createOrder(order: Order): ResultWrapper<Unit>

    suspend fun updateOrder(order: Order): ResultWrapper<Unit>

    suspend fun deleteOrder(order: Order): ResultWrapper<Unit>

    suspend fun getAllOrders(): ResultWrapper<List<Order>>

    suspend fun getOrder(orderId: Long): ResultWrapper<Order>

    suspend fun getHighestOrderId(): ResultWrapper<Order>

    suspend fun getOrdersFromTo(from: Long, to: Long): ResultWrapper<List<Order>>
}
