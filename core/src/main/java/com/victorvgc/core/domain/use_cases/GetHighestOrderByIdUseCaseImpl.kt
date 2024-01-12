package com.victorvgc.core.domain.use_cases

import com.victorvgc.core.domain.repositories.OrderRepository
import com.victorvgc.domain.core.Order
import com.victorvgc.utils.result_helper.ResultWrapper
import javax.inject.Inject

class GetHighestOrderByIdUseCaseImpl @Inject constructor(
    private val orderRepository: OrderRepository
) : GetHighestOrderByIdUseCase {
    override suspend fun invoke(): ResultWrapper<Order> = orderRepository.getHighestOrderId()
}
