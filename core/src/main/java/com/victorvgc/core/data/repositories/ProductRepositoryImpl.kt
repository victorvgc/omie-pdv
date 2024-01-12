package com.victorvgc.core.data.repositories

import com.victorvgc.core.domain.data_sources.ProductDataSource
import com.victorvgc.core.domain.repositories.ProductRepository
import com.victorvgc.domain.core.Product
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productDataSource: ProductDataSource
) : ProductRepository {
    override suspend fun createProduct(product: Product) = productDataSource.createProduct(product)

    override suspend fun deleteProduct(product: Product) = productDataSource.deleteProduct(product)

    override suspend fun updateProduct(product: Product) = productDataSource.updateProduct(product)

    override suspend fun getAllProducts() = productDataSource.getAllProducts()

    override suspend fun getProduct(productName: String) = productDataSource.getProduct(productName)
}
