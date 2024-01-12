package com.victorvgc.utils.extensions

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

private val decimalFormatSymbols = DecimalFormatSymbols().apply {
    decimalSeparator = ','
    groupingSeparator = '.'
}

fun BigDecimal.toCurrency(): String {
    val decimalFormat = DecimalFormat("#,##0.00", decimalFormatSymbols)

    return decimalFormat.format(this)
}

fun Long.toText(): String {
    val decimalFormat = DecimalFormat("#,##0", decimalFormatSymbols)

    return decimalFormat.format(this)
}

fun Int.toText(): String {
    val decimalFormat = DecimalFormat("#,##0", decimalFormatSymbols)

    return decimalFormat.format(this)
}
