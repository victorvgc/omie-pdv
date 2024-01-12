package com.victorvgc.core.domain.use_cases

import com.victorvgc.core.domain.repositories.OrderRepository
import com.victorvgc.domain.core.Order
import javax.inject.Inject

class SaveOrderUseCaseImpl @Inject constructor(
    private val orderRepository: OrderRepository
) : SaveOrderUseCase {
    override suspend fun invoke(order: Order) = orderRepository.createOrder(order)
}
