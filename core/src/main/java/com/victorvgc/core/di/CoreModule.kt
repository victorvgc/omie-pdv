package com.victorvgc.core.di

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.victorvgc.core.data.data_sources.ClientDataSourceImpl
import com.victorvgc.core.data.data_sources.OrderDataSourceImpl
import com.victorvgc.core.data.data_sources.ProductDataSourceImpl
import com.victorvgc.core.data.repositories.ClientRepositoryImpl
import com.victorvgc.core.data.repositories.OrderRepositoryImpl
import com.victorvgc.core.data.repositories.ProductRepositoryImpl
import com.victorvgc.core.domain.data_sources.ClientDataSource
import com.victorvgc.core.domain.data_sources.OrderDataSource
import com.victorvgc.core.domain.data_sources.ProductDataSource
import com.victorvgc.core.domain.repositories.ClientRepository
import com.victorvgc.core.domain.repositories.OrderRepository
import com.victorvgc.core.domain.repositories.ProductRepository
import com.victorvgc.core.domain.use_cases.DeleteClientUseCase
import com.victorvgc.core.domain.use_cases.DeleteClientUseCaseImpl
import com.victorvgc.core.domain.use_cases.DeleteOrderUseCase
import com.victorvgc.core.domain.use_cases.DeleteOrderUseCaseImpl
import com.victorvgc.core.domain.use_cases.DeleteProductUseCase
import com.victorvgc.core.domain.use_cases.DeleteProductUseCaseImpl
import com.victorvgc.core.domain.use_cases.GetAllClientsUseCase
import com.victorvgc.core.domain.use_cases.GetAllClientsUseCaseImpl
import com.victorvgc.core.domain.use_cases.GetAllOrdersUseCase
import com.victorvgc.core.domain.use_cases.GetAllOrdersUseCaseImpl
import com.victorvgc.core.domain.use_cases.GetAllProductsUseCase
import com.victorvgc.core.domain.use_cases.GetAllProductsUseCaseImpl
import com.victorvgc.core.domain.use_cases.GetHighestOrderByIdUseCase
import com.victorvgc.core.domain.use_cases.GetHighestOrderByIdUseCaseImpl
import com.victorvgc.core.domain.use_cases.GetOrderUseCase
import com.victorvgc.core.domain.use_cases.GetOrderUseCaseImpl
import com.victorvgc.core.domain.use_cases.SaveClientUseCase
import com.victorvgc.core.domain.use_cases.SaveClientUseCaseImpl
import com.victorvgc.core.domain.use_cases.SaveOrderUseCase
import com.victorvgc.core.domain.use_cases.SaveOrderUseCaseImpl
import com.victorvgc.core.domain.use_cases.SaveProductUseCase
import com.victorvgc.core.domain.use_cases.SaveProductUseCaseImpl
import com.victorvgc.core.domain.use_cases.UpdateOrderUseCase
import com.victorvgc.core.domain.use_cases.UpdateOrderUseCaseImpl
import com.victorvgc.core.domain.use_cases.UpdateProductUseCase
import com.victorvgc.core.domain.use_cases.UpdateProductUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class CoreSingletonModule {

    @Binds
    abstract fun bindClientDataSource(
        clientDataSourceImpl: ClientDataSourceImpl
    ): ClientDataSource

    @Binds
    abstract fun bindProductDataSource(
        productDataSourceImpl: ProductDataSourceImpl
    ): ProductDataSource

    @Binds
    abstract fun bindOrderDataSource(
        orderDataSourceImpl: OrderDataSourceImpl
    ): OrderDataSource

    @Binds
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository

    @Binds
    abstract fun bindClientRepository(
        clientRepositoryImpl: ClientRepositoryImpl
    ): ClientRepository

    @Binds
    abstract fun bindOrderRepository(
        orderRepositoryImpl: OrderRepositoryImpl
    ): OrderRepository
}

@Module
@InstallIn(ViewModelComponent::class)
object CoreUseCases {
    @Provides
    fun bindDeleteClientUseCase(
        clientRepository: ClientRepository
    ): DeleteClientUseCase = DeleteClientUseCaseImpl(clientRepository)

    @Provides
    fun bindDeleteProductUseCase(
        productRepository: ProductRepository
    ): DeleteProductUseCase = DeleteProductUseCaseImpl(productRepository)

    @Provides
    fun bindDeleteOrderUseCase(
        orderRepository: OrderRepository
    ): DeleteOrderUseCase = DeleteOrderUseCaseImpl(orderRepository)

    @Provides
    fun bindUpdateProductUseCase(
        productRepository: ProductRepository
    ): UpdateProductUseCase = UpdateProductUseCaseImpl(productRepository)

    @Provides
    fun bindUpdateOrderUseCase(
        orderRepository: OrderRepository
    ): UpdateOrderUseCase = UpdateOrderUseCaseImpl(orderRepository)

    @Provides
    fun bindSaveClientUseCase(
        clientRepository: ClientRepository
    ): SaveClientUseCase = SaveClientUseCaseImpl(clientRepository)

    @Provides
    fun bindSaveProductUseCase(
        productRepository: ProductRepository
    ): SaveProductUseCase = SaveProductUseCaseImpl(productRepository)

    @Provides
    fun bindSaveOrderUseCase(
        orderRepository: OrderRepository
    ): SaveOrderUseCase = SaveOrderUseCaseImpl(orderRepository)

    @Provides
    fun bindGetAllClientsUseCase(
        clientRepository: ClientRepository
    ): GetAllClientsUseCase = GetAllClientsUseCaseImpl(clientRepository)

    @Provides
    fun bindGetAllProductsUseCase(
        productRepository: ProductRepository
    ): GetAllProductsUseCase = GetAllProductsUseCaseImpl(productRepository)

    @Provides
    fun provideGetAllOrderUseCase(
        orderRepository: OrderRepository
    ): GetAllOrdersUseCase = GetAllOrdersUseCaseImpl(orderRepository)

    @Provides
    fun provideGetOrderUseCase(
        orderRepository: OrderRepository
    ): GetOrderUseCase = GetOrderUseCaseImpl(orderRepository)

    @Provides
    fun provideGetHighestOrderByIdUseCase(
        orderRepository: OrderRepository
    ): GetHighestOrderByIdUseCase = GetHighestOrderByIdUseCaseImpl(orderRepository)
}

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore = Firebase.firestore
}

