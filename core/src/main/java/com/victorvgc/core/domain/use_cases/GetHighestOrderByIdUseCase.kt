package com.victorvgc.core.domain.use_cases

import com.victorvgc.domain.core.Order
import com.victorvgc.utils.result_helper.ResultWrapper

interface GetHighestOrderByIdUseCase {
    suspend operator fun invoke(): ResultWrapper<Order>
}
