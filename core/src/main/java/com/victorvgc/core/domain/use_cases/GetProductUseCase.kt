package com.victorvgc.core.domain.use_cases

import com.victorvgc.domain.core.Product
import com.victorvgc.utils.result_helper.ResultWrapper

interface GetProductUseCase {

    suspend operator fun invoke(productName: String): ResultWrapper<Product>
}
