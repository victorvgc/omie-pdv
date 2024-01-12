package com.victorvgc.core.domain.use_cases

import com.victorvgc.domain.core.Product
import com.victorvgc.utils.result_helper.ResultWrapper

interface GetAllProductsUseCase {
    suspend operator fun invoke(): ResultWrapper<List<Product>>
}
