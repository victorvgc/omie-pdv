package com.victorvgc.domain.core

import com.victorvgc.domain.test_utils.clientTest
import com.victorvgc.domain.test_utils.productTest
import java.math.BigDecimal
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class OrderTest {

    private lateinit var order: Order

    @Before
    fun setup() {
        order = Order(id = 1, client = clientTest)
    }

    @Test
    fun `when instantiate then calculate total value to be zero`() {
        // arrange

        // act
        val result = order.totalPrice

        // assert
        assertEquals(BigDecimal.ZERO, result)

    }

    @Test
    fun `when addProduct then calculate total value to new value`() {
        // arrange
        val quantity: Long = 12

        // act
        order.addProduct(product = productTest, quantity = quantity)
        val result = order.totalPrice

        // assert
        assertEquals(productTest.unitPrice.times(BigDecimal(quantity)), result)

    }

    @Test
    fun `when removeProduct then calculate total value to new value`() {
        // arrange
        val quantity: Long = 12
        order.addProduct(product = productTest, quantity = quantity)

        // act
        order.removeProduct(product = productTest)
        val result = order.totalPrice

        // assert
        assertEquals(BigDecimal.ZERO, result)

    }

    @Test
    fun `when instantiate then calculate total units to be zero`() {
        // arrange

        // act
        val result = order.totalUnits

        // assert
        assertEquals(0, result)

    }

    @Test
    fun `when addProduct then calculate total units to new value`() {
        // arrange
        val quantity: Long = 12

        // act
        order.addProduct(product = productTest, quantity = quantity)
        val result = order.totalUnits

        // assert
        assertEquals(quantity, result)

    }

    @Test
    fun `when removeProduct then calculate total unit to new value`() {
        // arrange
        val quantity: Long = 12
        order.addProduct(product = productTest, quantity = quantity)

        // act
        order.removeProduct(product = productTest)
        val result = order.totalUnits

        // assert
        assertEquals(0, result)

    }

    @Test
    fun `when instantiate then return an empty products list`() {
        // arrange

        // act
        val result = order.productList

        // assert
        assertEquals(emptyList(), result)

    }

    @Test
    fun `when addProduct then return a products list with that product in it`() {
        // arrange
        val quantity: Long = 12

        // act
        order.addProduct(product = productTest, quantity = quantity)
        val result = order.productList

        // assert
        val expected = listOf(OrderProduct(productTest, quantity))

        assertEquals(expected, result)

    }

    @Test
    fun `when removeProduct then return a products list with that product in it`() {
        // arrange
        val quantity: Long = 12
        order.addProduct(product = productTest, quantity = quantity)

        // act
        order.removeProduct(product = productTest)
        val result = order.productList

        // assert
        assertEquals(emptyList(), result)

    }
}
