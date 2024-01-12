package com.victorvgc.core.domain.use_cases

import com.victorvgc.core.domain.repositories.ProductRepository
import com.victorvgc.domain.core.Product
import javax.inject.Inject

class DeleteProductUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository
) : DeleteProductUseCase {
    override suspend fun invoke(product: Product) = productRepository.deleteProduct(product)
}
