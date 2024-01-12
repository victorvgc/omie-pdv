package com.victorvgc.core.domain.use_cases

import com.victorvgc.core.domain.repositories.OrderRepository
import com.victorvgc.domain.core.Order
import javax.inject.Inject

class UpdateOrderUseCaseImpl @Inject constructor(
    private val orderRepository: OrderRepository
) : UpdateOrderUseCase {
    override suspend fun invoke(order: Order) = orderRepository.updateOrder(order)
}
