package com.victorvgc.order_screen.domain.models

import com.victorvgc.domain.core.Order

data class OrderListScreenState(
    val orderList: List<Order> = emptyList(),
    val openOrderId: Long = 0
)
