package com.victorvgc.core.domain.data_sources

import com.victorvgc.domain.core.Product
import com.victorvgc.utils.result_helper.ResultWrapper

interface ProductDataSource {
    suspend fun createProduct(product: Product): ResultWrapper<Unit>

    suspend fun deleteProduct(product: Product): ResultWrapper<Unit>

    suspend fun updateProduct(product: Product): ResultWrapper<Unit>

    suspend fun getAllProducts(): ResultWrapper<List<Product>>

    suspend fun getProduct(productName: String): ResultWrapper<Product>
}
