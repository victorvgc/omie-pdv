package com.victorvgc.core.domain.use_cases

import com.victorvgc.core.domain.repositories.OrderRepository
import java.util.Date
import javax.inject.Inject

class GetOrdersFromToUseCaseImpl @Inject constructor(
    private val orderRepository: OrderRepository
) : GetOrdersFromToUseCase {
    override suspend fun invoke(from: Date, to: Date) = orderRepository.getOrdersFromTo(
        from = from.time,
        to = to.time
    )
}
