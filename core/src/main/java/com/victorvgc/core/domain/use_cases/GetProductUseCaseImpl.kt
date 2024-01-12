package com.victorvgc.core.domain.use_cases

import com.victorvgc.core.domain.repositories.ProductRepository
import javax.inject.Inject

class GetProductUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository
) : GetProductUseCase {
    override suspend fun invoke(productName: String) = productRepository.getProduct(productName)
}
