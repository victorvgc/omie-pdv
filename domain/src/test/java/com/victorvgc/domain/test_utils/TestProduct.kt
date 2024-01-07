package com.victorvgc.domain.test_utils

import com.victorvgc.domain.core.Product
import java.math.BigDecimal


val productName = "product"
val productUnitPrice = BigDecimal(1)

val productTest = Product(
    name = productName,
    unitPrice = productUnitPrice
)
