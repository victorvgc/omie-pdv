package com.victorvgc.core.domain.use_cases

import com.victorvgc.core.domain.repositories.OrderRepository
import javax.inject.Inject

class GetOrderUseCaseImpl @Inject constructor(
    private val orderRepository: OrderRepository
) : GetOrderUseCase {
    override suspend fun invoke(orderId: Long) = orderRepository.getOrder(orderId)
}
