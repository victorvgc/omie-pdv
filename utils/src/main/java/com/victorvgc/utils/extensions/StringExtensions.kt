package com.victorvgc.utils.extensions

fun String.removeStartingTrailingZeros(): String {
    if (this.isEmpty())
        return this

    var firstNumIndex = 0

    for (index in indices) {
        if (this[index].digitToInt() == 0) {
            continue
        } else {
            firstNumIndex = index
            break
        }
    }

    return this.substring(firstNumIndex, length)
}
