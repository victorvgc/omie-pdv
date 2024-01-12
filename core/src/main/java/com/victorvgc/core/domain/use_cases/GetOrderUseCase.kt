package com.victorvgc.core.domain.use_cases

import com.victorvgc.domain.core.Order
import com.victorvgc.utils.result_helper.ResultWrapper

interface GetOrderUseCase {

    suspend operator fun invoke(orderId: Long): ResultWrapper<Order>
}
