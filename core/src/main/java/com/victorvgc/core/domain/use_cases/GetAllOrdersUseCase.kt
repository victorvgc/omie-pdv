package com.victorvgc.core.domain.use_cases

import com.victorvgc.domain.core.Order
import com.victorvgc.utils.result_helper.ResultWrapper

interface GetAllOrdersUseCase {
    suspend operator fun invoke(): ResultWrapper<List<Order>>
}
