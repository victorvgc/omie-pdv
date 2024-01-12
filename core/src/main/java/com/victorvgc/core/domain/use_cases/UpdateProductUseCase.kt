package com.victorvgc.core.domain.use_cases

import com.victorvgc.domain.core.Product
import com.victorvgc.utils.result_helper.ResultWrapper

interface UpdateProductUseCase {

    suspend operator fun invoke(product: Product): ResultWrapper<Unit>
}
