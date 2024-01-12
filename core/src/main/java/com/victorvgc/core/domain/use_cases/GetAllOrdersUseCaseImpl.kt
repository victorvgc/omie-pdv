package com.victorvgc.core.domain.use_cases

import com.victorvgc.core.domain.repositories.OrderRepository
import javax.inject.Inject

class GetAllOrdersUseCaseImpl @Inject constructor(
    private val orderRepository: OrderRepository
) : GetAllOrdersUseCase {
    override suspend fun invoke() = orderRepository.getAllOrders()
}
