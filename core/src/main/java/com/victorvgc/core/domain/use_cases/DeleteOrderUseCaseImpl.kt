package com.victorvgc.core.domain.use_cases

import com.victorvgc.core.domain.repositories.OrderRepository
import com.victorvgc.domain.core.Order
import javax.inject.Inject

class DeleteOrderUseCaseImpl @Inject constructor(
    private val orderRepository: OrderRepository
) : DeleteOrderUseCase {
    override suspend fun invoke(order: Order) = orderRepository.deleteOrder(order)
}
