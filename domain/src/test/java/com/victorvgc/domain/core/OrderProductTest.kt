package com.victorvgc.domain.core

import com.victorvgc.domain.test_utils.productTest
import java.math.BigDecimal
import org.junit.Test
import kotlin.test.assertEquals

class OrderProductTest {

    private lateinit var orderProduct: OrderProduct

    @Test
    fun `when instantiate then calculate total price`() {
        // arrange
        val quantity: Long = 12

        orderProduct = OrderProduct(
            productTest,
            quantity = quantity
        )

        // act
        val result = orderProduct.totalPrice

        // assert
        val expected = productTest.unitPrice.times(BigDecimal(quantity))

        assertEquals(expected, result)
    }
}
