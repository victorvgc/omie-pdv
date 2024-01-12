package com.victorvgc.core.data.repositories

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.victorvgc.core.domain.data_sources.OrderDataSource
import com.victorvgc.core.domain.repositories.OrderRepository
import com.victorvgc.core.test_utils.CoroutineRule
import com.victorvgc.domain.core.Order
import com.victorvgc.utils.failures.FailureCodes
import com.victorvgc.utils.result_helper.ResultWrapper
import com.victorvgc.utils.result_helper.toSuccess
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class OrderRepositoryTest {

    @get:Rule
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutineRule()

    private lateinit var orderRepository: OrderRepository

    private val mockOrderDataSource = mockk<OrderDataSource>()

    @Before
    fun setup() {
        orderRepository = OrderRepositoryImpl(mockOrderDataSource)
    }

    @Test
    fun `when createOrder then call the proper data source method`() = runTest {
        // arrange
        coEvery {
            mockOrderDataSource.createOrder(any())
        } returns Unit.toSuccess()

        // act
        orderRepository.createOrder(Order.Empty)

        // assert
        coVerify {
            mockOrderDataSource.createOrder(Order.Empty)
        }
    }

    @Test
    fun `when createOrder then return the proper response from data source method`() = runTest {
        // arrange
        coEvery {
            mockOrderDataSource.createOrder(any())
        } returns Unit.toSuccess()

        // act
        val result = orderRepository.createOrder(Order.Empty)

        // assert
        assertEquals(Unit.toSuccess(), result)
    }

    @Test
    fun `when createOrder fails then return the proper response from data source method`() =
        runTest {
            // arrange
            coEvery {
                mockOrderDataSource.createOrder(any())
            } returns ResultWrapper.Failure(FailureCodes.FAILURE_CODE_CREATE)

            // act
            val result = orderRepository.createOrder(Order.Empty)

            // assert
            val expected = ResultWrapper.Failure<Unit>(FailureCodes.FAILURE_CODE_CREATE)

            assertEquals(expected, result)
        }
}
