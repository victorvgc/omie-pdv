package com.victorvgc.core.domain.use_cases

import com.victorvgc.core.domain.repositories.ProductRepository
import com.victorvgc.domain.core.Product
import javax.inject.Inject

class SaveProductUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository
) : SaveProductUseCase {
    override suspend fun invoke(product: Product) = productRepository.createProduct(product)
}
