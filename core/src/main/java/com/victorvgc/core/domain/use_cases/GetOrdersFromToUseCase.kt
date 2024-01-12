package com.victorvgc.core.domain.use_cases

import com.victorvgc.domain.core.Order
import com.victorvgc.utils.result_helper.ResultWrapper
import java.util.Date

interface GetOrdersFromToUseCase {

    suspend operator fun invoke(from: Date, to: Date): ResultWrapper<List<Order>>
}
