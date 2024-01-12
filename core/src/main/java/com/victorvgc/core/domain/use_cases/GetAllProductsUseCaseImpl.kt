package com.victorvgc.core.domain.use_cases

import com.victorvgc.core.domain.repositories.ProductRepository
import javax.inject.Inject

class GetAllProductsUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository
) : GetAllProductsUseCase {
    override suspend fun invoke() = productRepository.getAllProducts()
}
